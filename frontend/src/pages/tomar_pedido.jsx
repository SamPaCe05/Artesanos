import './tomar_pedido.css'
import arrow from '../assets/flecha.png'

const TomarPedido= ()=>{
    return(
        <>
            <section className='tomar-pedido-sec'>
                <div className='tomar-pedido-div-uno'>
                    <button className='tomar-pedido-boton-volver'>
                        <img src={arrow} alt="" />
                    </button>
                </div>
                <div className='tomar-pedido-div-dos'>
                    <label htmlFor="">Número de mesa</label>
                    <input type="text" placeholder='Ej: mesa 1'/>
                </div>
                <div className='tomar-pedido-div-tres'>

                    <div>
                        <h3>Pedido</h3>
                    </div>

                    <div>
                    
                    </div>

                </div>
                <div className='tomar-pedido-div-cuatro'>
                    <button>Añadir producto</button>
                </div>
                <div className='tomar-pedido-div-cinco'>
                    <button>Confirmar pedido</button>
                    <h3>Total</h3>
                    <h3>$</h3>
                </div>
            </section>
        </>
    )
}

export default TomarPedido