
package com.artesanos.sistema_pedidos.services;

import com.artesanos.sistema_pedidos.dtos.ProductoDetalleDto;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.image.*;
import com.github.anastaciocintra.output.TcpIpOutputStream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class NetworkPrinterService {

    private final ResourceLoader resourceLoader;

    private static final int PRINTER_CHAR_WIDTH = 48;
    private final Style boldCenter = new Style().setBold(true).setFontSize(Style.FontSize._1, Style.FontSize._1)
            .setJustification(EscPosConst.Justification.Center);
    private final Style boldLeft = new Style().setBold(true);
    private final Style boldCenterBig = new Style().setFontSize(Style.FontSize._1, Style.FontSize._2).setBold(true)
            .setJustification(EscPosConst.Justification.Center);
    private final Style normal = new Style().setFontSize(Style.FontSize._1, Style.FontSize._1);

    public NetworkPrinterService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void imprimirFactura(Map<String, Object> data, String ipImpresora) throws IOException {
        try (TcpIpOutputStream outputStream = new TcpIpOutputStream(ipImpresora, 9100);
                EscPos escpos = new EscPos(outputStream)) {

            printLogo(escpos);

            escpos.writeLF(boldCenter, "Pedido #" + data.getOrDefault("id", ""));

            imprimirEncabezadoComun(escpos, data);

            String cabecera = format3ColumnsMultiLineInvoice("CANT", "PRODUCTO", "TOTAL", PRINTER_CHAR_WIDTH).get(0);
            escpos.writeLF(boldLeft, cabecera);
            escpos.writeLF("------------------------------------------------");

            @SuppressWarnings("unchecked")
            List<ProductoDetalleDto> productos = (List<ProductoDetalleDto>) data.get("pedido");

            if (productos != null) {
                for (ProductoDetalleDto prod : productos) {
                    String cant = prod.getCantidadProducto() != null ? String.valueOf(prod.getCantidadProducto()) : "1";
                    String nombre = prod.getNombreProducto() != null ? prod.getNombreProducto() : "Producto";

                    Integer subtotalRaw = prod.getSubtotalPedido() != null ? prod.getSubtotalPedido() : 0;
                    String subtotalFormateado = "$" + formatCurrency(subtotalRaw);

                    List<String> lineasProducto = format3ColumnsMultiLineInvoice(cant, nombre, subtotalFormateado,
                            PRINTER_CHAR_WIDTH);

                    for (String linea : lineasProducto) {
                        escpos.writeLF(normal, linea);
                    }

                }
            }
            escpos.writeLF("------------------------------------------------");

            Integer totalRaw = (Integer) data.getOrDefault("total", 0);
            String totalFormateado = formatCurrency(totalRaw);

            String totalFormat = padLeft("TOTAL: $" + totalFormateado, PRINTER_CHAR_WIDTH);
            escpos.writeLF(new Style().setBold(true), totalFormat);
            finalizarTicket(escpos, "Gracias por su compra");

        } catch (Exception e) {
            throw new IOException("Fallo en la impresión: " + e.getMessage(), e);
        }
    }

    public void imprimirCocina(Map<String, Object> data, String ipImpresora) throws IOException {
        try (TcpIpOutputStream outputStream = new TcpIpOutputStream(ipImpresora, 9100);
                EscPos escpos = new EscPos(outputStream)) {
            Style normalCocinaStyle = new Style().setFontSize(Style.FontSize._1, Style.FontSize._2);
            printLogo(escpos);

            escpos.writeLF(boldCenter, "Pedido #" + data.getOrDefault("id", ""));
            imprimirEncabezadoComun(escpos, data);

            String cabecera = format3ColumnsMultiLineKitchen("CANT", "PRODUCTO", "PETICION", PRINTER_CHAR_WIDTH).get(0);
            escpos.writeLF(boldLeft, cabecera);
            escpos.writeLF("------------------------------------------------");

            @SuppressWarnings("unchecked")
            List<ProductoDetalleDto> productos = (List<ProductoDetalleDto>) data.get("pedido");

            if (productos != null) {
                for (ProductoDetalleDto prod : productos) {
                    String cant = prod.getCantidadProducto() != null ? String.valueOf(prod.getCantidadProducto()) : "1";
                    String nombre = prod.getNombreProducto() != null ? prod.getNombreProducto() : "Producto";
                    String peticion = (prod.getPeticionCliente() != null && !prod.getPeticionCliente().isBlank())
                            ? prod.getPeticionCliente()
                            : "";
                    List<String> lineasProducto = format3ColumnsMultiLineKitchen(cant, nombre, peticion,
                            PRINTER_CHAR_WIDTH);

                    for (String linea : lineasProducto) {
                        escpos.writeLF(normalCocinaStyle, linea);
                    }

                }
            }
            escpos.writeLF("------------------------------------------------");
            finalizarTicket(escpos, "Fin comanda");

        } catch (Exception e) {
            throw new IOException("Fallo en la impresión: " + e.getMessage(), e);
        }
    }

    private void printLogo(EscPos escpos) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/images/artesanosFactura.jpg");

        try (InputStream is = resource.getInputStream()) {
            BufferedImage originalImage = ImageIO.read(is);

            int targetWidth = 300;
            BufferedImage resized = resizeImage(originalImage, targetWidth);

            Bitonal algorithm = new BitonalThreshold(127);
            EscPosImage escposImage = new EscPosImage(new CoffeeImageImpl(resized), algorithm);

            RasterBitImageWrapper imageWrapper = new RasterBitImageWrapper();
            imageWrapper.setJustification(EscPosConst.Justification.Center);

            escpos.write(imageWrapper, escposImage);
            escpos.feed(1);
        }
    }

    private BufferedImage resizeImage(BufferedImage original, int targetWidth) {
        int width = (targetWidth / 8) * 8;
        int height = (int) ((double) original.getHeight() / original.getWidth() * width);

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = resized.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();
        return resized;
    }

    private void imprimirEncabezadoComun(EscPos escpos, Map<String, Object> data) throws IOException {
        if (data.get("mesa") != null)
            escpos.writeLF(boldCenterBig, "Mesa: " + data.get("mesa"));
        if (data.get("nombreDomicilio") != null && !data.get("nombreDomicilio").toString().isBlank()) {
            escpos.writeLF(boldCenter, "Teléfono: "+  data.get("numeroCliente"));
            escpos.write("");
            escpos.writeLF(boldCenterBig, "Domicilio: " + data.get("nombreDomicilio"));
        }
        
        escpos.writeLF("------------------------------------------------");
    }

    private void finalizarTicket(EscPos escpos, String mensaje) throws IOException {
        escpos.feed(1);
        escpos.writeLF(boldCenter, mensaje);
        escpos.feed(5);
        escpos.cut(EscPos.CutMode.FULL);
    }

    private String formatCurrency(Integer monto) {
        if (monto == null)
            return "0";
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("es", "CO"));
        return formatter.format(monto);
    }

    private List<String> format3ColumnsMultiLineInvoice(String cant, String desc, String total, int totalWidth) {
        int cantWidth = 5;
        int totalWidthCol = 12;
        int descWidth = totalWidth - cantWidth - totalWidthCol;

        List<String> lines = new ArrayList<>();

        List<String> descLines = splitTextIntoLines(desc != null ? desc : "", descWidth - 1);

        for (int i = 0; i < descLines.size(); i++) {
            String c1 = (i == 0) ? padRight(cant, cantWidth) : padRight("", cantWidth);
            String c2 = padRight(descLines.get(i), descWidth);
            String c3 = (i == 0) ? padLeft(total, totalWidthCol) : padLeft("", totalWidthCol);

            lines.add(c1 + c2 + c3);
        }

        return lines;
    }

    private List<String> format3ColumnsMultiLineKitchen(String cant, String desc, String peticion, int totalWidth) {
        int cantWidth = 5;
        int peticionWidthCol = 16;
        int descWidth = totalWidth - cantWidth - peticionWidthCol;

        List<String> lines = new ArrayList<>();
        List<String> descLines = splitTextIntoLines(desc != null ? desc : "", descWidth - 1);
        List<String> peticionLines = splitTextIntoLines(peticion != null ? peticion : "", peticionWidthCol - 1);

        int maxLines = Math.max(descLines.size(), peticionLines.size());
        for (int i = 0; i < maxLines; i++) {
            String c1 = (i == 0) ? padRight(cant, cantWidth) : padRight("", cantWidth);
            String currentDesc = (i < descLines.size()) ? descLines.get(i) : "";
            String c2 = padRight(currentDesc, descWidth);
            String currentPeticion = (i < peticionLines.size()) ? peticionLines.get(i) : "";
            String c3 = padRight(currentPeticion, peticionWidthCol);

            lines.add(c1 + c2 + c3);
        }

        return lines;
    }

    private List<String> splitTextIntoLines(String text, int maxLength) {
        List<String> result = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            result.add("");
            return result;
        }

        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > maxLength) {
                if (currentLine.length() > 0) {
                    result.add(currentLine.toString());
                    currentLine = new StringBuilder();
                }
                while (word.length() > maxLength) {
                    result.add(word.substring(0, maxLength));
                    word = word.substring(maxLength);
                }
            }
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }
        if (currentLine.length() > 0) {
            result.add(currentLine.toString());
        }
        return result;
    }

    private String padRight(String s, int n) {
        if (s == null)
            s = "";
        if (s.length() >= n)
            return s.substring(0, n);
        return String.format("%-" + n + "s", s);
    }

    private String padLeft(String s, int n) {
        if (s == null)
            s = "";
        if (s.length() >= n)
            return s.substring(0, n);
        return String.format("%" + n + "s", s);
    }
}