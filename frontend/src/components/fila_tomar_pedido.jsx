import { useState } from 'react';
import './fila_tomar_pedido.css'




const FilaTomarPedido=()=>{
    const [cnt,setcnt]=useState(0);

    const incrementar=()=>{
        setcnt((tmp)=>tmp+1);
    }

    const decrementar=()=>{
        setcnt((tmp)=>Math.max(0,tmp-1))
    }
    return(
        <>
            <section className='fila-tomar-pedido-sec'>
                <div className='fila-tomar-pedido-div-uno'>{cnt}</div>  
                <div className='fila-tomar-pedido-div-dos'>Pizza Espanta Brujas</div>  
                <div className='fila-tomar-pedido-div-tres' onClick={incrementar}>+</div>  
                <div className='fila-tomar-pedido-div-cuatro' onClick={decrementar}>-</div>  
                <div className='fila-tomar-pedido-div-cinco'>$20.000</div>  
            </section>
        </>
    )
}

export default FilaTomarPedido