import './gestion_productos.css'
import Boton from '../components/boton_gestion'
import nuevo from '../assets/nuevo-producto.png'
import lupa from '../assets/lupa.png'
import { useNavigate } from 'react-router-dom'
import arrow from '../assets/flecha.png'

const GestionProductos = () => {
    const navigate=useNavigate();
    return (
        <>
            <section className='gestion_sec'>
                <button className='boton-gestion-productos' onClick={()=>navigate('/caja')}><img src={arrow} alt="" /></button>
                <div className='boton-numero-uno-gestion'>
                    <Boton imagen={nuevo} className={"boton"} nombre="Crear producto" ruta="/crear-producto"/>
                </div>
                
                <div className='boton-numero-dos-gestion'>
                    <Boton imagen={lupa} className={"boton"} nombre="Buscar producto" ruta="/buscar-producto"/>    
                </div>
                
            </section>
        </>
    )
}

export default GestionProductos