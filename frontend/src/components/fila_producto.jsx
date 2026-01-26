import './fila_producto.css'

const FilaProducto=({campoUno,campoDos,campoTres,campoCuatro})=>{
    return(
        <>
            <section className='fila-producto-sec'>
                <div className='uno'>{campoUno}</div>
                <div className='dos'>{campoDos}</div>
                <div className='tres'>{campoTres}</div>
                <div className='cuatro'>{campoCuatro}</div>
            </section>
        </>
    )
}

export default FilaProducto