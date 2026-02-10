import { forwardRef } from 'react'
import './comanda.css'

const Comanda = forwardRef(({ pedido }, ref)=> {
    return (

        <div ref={ref} className='div-comanda'>
            <h1>Artesanos Pizza</h1>
            <h2>Pedido N.</h2>
        </div>

    )
})

export default Comanda