import "./App.css";
import { Routes, Route } from "react-router-dom";
import Login from "./pages/login";
import HomeCaja from "./pages/home_caja";
import GestionarProductos from "./pages/gestion_productos";
import Pedidos from "./pages/pedidos";
import FilaProducto from "./components/fila_producto";
import BuscarProducto from "./pages/buscar_producto";
import VerPedido from "./pages/ver_pedido";
import PedidoMesera from "./pages/pedidos_mesera";
import TomarPedido from "./pages/tomar_pedido";
import GestionProductos from "./pages/gestion_productos";
import CrearProducto from "./pages/crear_producto";
import VerVentas from "./pages/ver_ventas";
import { ToastContainer } from 'react-toastify';
function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/caja" element={<HomeCaja />} />
        <Route path="/mesera" element={<PedidoMesera />} />
        <Route path="/gestion-productos" element={<GestionProductos />} />
        <Route path="/pedidos" element={<Pedidos />} />
        <Route path="/buscar-producto" element={<BuscarProducto />} />
        <Route path="/ver-pedido/:id/:mesa" element={<VerPedido />} />
        <Route path="/tomar-pedido" element={<TomarPedido />} />
        <Route path="/tomar-pedido/:id/:mesa" element={<TomarPedido />} />
        <Route path="/crear-producto" element={<CrearProducto />} />
        <Route
          path="/editar-producto/:id/:nombre/:precio"
          element={<CrearProducto />}
        />
        <Route path="/ver-ventas" element={<VerVentas />} />
        <Route path="/ver-pedido/:id/:mesa/:estado" element={<VerPedido />} />
      </Routes>

      <ToastContainer position="bottom-right" autoClose={2500} />
    </>
  );
}

export default App;
