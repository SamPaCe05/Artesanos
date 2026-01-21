import './pedidos.css'
import BotonPedido from '../components/boton_pedido'

const Pedidos=()=>{
    return(
        <>
        <section className='pedidos_section'>
            <h2>Pedidos</h2>
            <div className='scroll_pedidos'>
                <BotonPedido/>
                <BotonPedido/>
                <BotonPedido/>
                <BotonPedido/>
                <BotonPedido/>
                <BotonPedido/>
            </div>
        </section>
        </>
    )
}

export default Pedidos