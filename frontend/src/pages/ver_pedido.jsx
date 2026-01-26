import './ver_pedido.css'
import FilaPedido from '../components/fila_pedido'

const VerPedido=({n_pedido, n_mesa})=>{
    return(
        <>
            <section className='fila-pedido-sec'>
                <div className='fila-pedido-div-uno'>
                    <div className='fila-pedido-text-pedido'>
                        <h3>Pedido {n_pedido}</h3>
                    </div>
                    <div className='fila-pedido-text-mesa'>
                        <h3>Mesa N.{n_mesa}</h3>
                    </div>
                </div>
                <div className='fila-pedido-div-dos'>
                    <div className='fila-pedido-index'>
                        <h3>Productos</h3>
                        <h3>Cantidad</h3>
                        <h3>Precio</h3>
                        <h3>Total</h3>
                    </div>
                    <div className='filas_ver'>
                        <FilaPedido/>
                        <FilaPedido/>
                        
                        
                    </div>
                    <div className='fila-pedido-total'>
                        <h3>Total</h3>
                        <div>$</div>
                    </div>
                </div>
                <div className='fila-pedido-div-tres'>
                    <div>
                        <button className='fila-boton-uno'>Anular Pedido</button>
                        </div>
                    <div>
                        <button className='fila-boton-dos'>Confirmar Pago</button>
                    </div>
                </div>
            </section>
        </>
    )
}

export default VerPedido