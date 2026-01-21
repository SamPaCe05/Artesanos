import './boton_pedido.css'

const BotonPedido= ({num_pedido, num_mesa, total})=>{
    return(
        <>
        <div className='boton_pedido_cont'>
            <div>
                <h3>Pedido {num_pedido}</h3>
                <h3>Mesa N.{num_mesa}</h3>
            </div>
            <div>
                <h5>Total</h5>
                <h5>${total}</h5>
            </div>
        </div>
        </>
    )
}

export default BotonPedido