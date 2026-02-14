import { forwardRef } from 'react'
import './comanda.css'
import { formateador } from './ver_ventas'
import logo from '../assets/artesanosFactura.jpg'
const Comanda = forwardRef(({ pedido, id, mesa, total }, ref) => {
    return (

        <div ref={ref} className='div-comanda'>
            <div className='comanda-header'>
                <img 
                    src={logo} 
                    alt="Logo Artesanos" 
                    className='logo-comanda'
                />
                <h2>Pedido N. {id}</h2>
                <p className='mesa'>Mesa {mesa}</p>
            </div>

            <div className='sec-dos-imprimir'>
                <div className='cabeceras'>
                    <p>Productos</p>
                    <p>Cantidad</p>
                    <p>Subtotal</p>
                </div>
                <div className='articulos'>

                    {
                        pedido.map(p => (
                            <div className='items-pedido'>
                                <p className='name-prd-comanda'>{p.nombreProducto.charAt(0).toUpperCase() + p.nombreProducto.slice(1)}</p>
                                <p>{p.cantidadProducto}</p>
                                <p>{formateador.format(p.subtotalPedido)}</p>
                            </div>
                        ))
                    }
                    <div className='totales-pedido'>
                        <h3>Total {formateador.format(total)}</h3>
                    </div>
                </div>

            </div>
        </div>

    )
})

export default Comanda