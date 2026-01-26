import './pedidos_mesera.css'
import BotonPedido from '../components/boton_pedido'
import volver from '../assets/flecha.png'

const PedidoMesera = () => {
    return (
        <>
            <section className='pedido-mesera-sec'>
                <div className='pedido-mesera-head'>
                    <button className='boton-arrow'>
                        <img src={volver} alt="" />
                    </button>
                    <button className='boton-nuevo-pedido'>
                        + Nuevo pedido
                        </button>
                </div>

                <div className='pedido-mesera-pedidos'>
                    <h3 className='title-pedido'>Pedidos</h3>
                    <BotonPedido />
                </div>
            </section>
        </>
    )
}

export default PedidoMesera