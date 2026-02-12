import { useState } from 'react';
import './fila_tomar_pedido.css'
import { formateador } from '../pages/ver_ventas';




const FilaTomarPedido = ({ nombre_producto, funcion, index, precio, cantidad }) => {



    const [cnt, setcnt] = useState(cantidad);
    const [valor, setvalor] = useState(cnt * precio)


    const incrementar = () => {
        setcnt((tmp) => tmp + 1);
        setvalor(precio * (cnt + 1))
        funcion(cnt + 1, index, (precio * (cnt + 1)))
    }
    const cambiar_subtotal = () => {
        setvalor(precio * cnt)
    }


    const decrementar = () => {
        setcnt((tmp) => Math.max(0, tmp - 1))
        setvalor(precio * (cnt - 1))
        funcion(cnt - 1, index, (precio * (cnt - 1)))
    }
    return (
        <>
            <section className='fila-tomar-pedido-sec'>
                <div className='fila-tomar-pedido-div-uno'>{cnt}</div>
                <div className='fila-tomar-pedido-div-dos'>{nombre_producto}</div>
                <div className='fila-tomar-pedido-div-tres' onClick={incrementar}>+</div>
                <div className='fila-tomar-pedido-div-cuatro' onClick={decrementar}>-</div>
                <div className='fila-tomar-pedido-div-cinco'>{formateador.format(valor)}</div>
            </section>
        </>
    )
}

export default FilaTomarPedido