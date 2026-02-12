import { forwardRef } from 'react'
import './comanda.css'

const Comanda = forwardRef(({ pedido, id, mesa, total }, ref) => {
    return (

        <div ref={ref} className='div-comanda'>
            <div>
                <h1>Artesanos Pizza</h1>
                <h2>Pedido N. {id}</h2>
                <p>Mesa {mesa}</p>
            </div>

            <div className='sec-dos-imprimir'>
                <div className='cabeceras'>
                    <p>Productos</p>
                    <p>Cnt</p>
                    <p>Subtotal</p>
                </div>
                <div className='articulos'>

                    {
                        pedido.map(p => (
                            <div className='items-pedido'>
                                <p>{p.nombreProducto}</p>
                                <p>{p.cantidadProducto}</p>
                                <p>{p.subtotalPedido}</p>
                            </div>
                        ))
                    }
                    <div className='totales-pedido'>
                        <h3>Total {total}</h3>
                    </div>
                </div>

            </div>
        </div>

    )
})

export default Comanda