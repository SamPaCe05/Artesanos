import './home_caja.css'
import Boton from '../components/boton_gestion'
import alcancia from '../assets/hucha.png'
import pizza from '../assets/pizza.png'
import impresora from '../assets/impresora.png'


const HomeCaja=()=>{
    return( 
        <>
            <section className='caja'>
                <Boton imagen={alcancia} nombre="Buscar ventas"/>   
                <Boton imagen={pizza} nombre="Gestionar productos"/>   
                <Boton imagen={impresora} nombre="Pedidos"/>    
            </section>
        </>
    )
}

export default HomeCaja