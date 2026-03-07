import { useNavigate } from 'react-router-dom'
import './gestionar_domis.css'
import volver from '../assets/flecha.png'
import Boton from '../components/boton_gestion'
import editarDomi from '../assets/nota.png'
import nuevoDomi from '../assets/entrega.png'

const GestionDomis=()=>{
    const navigate=useNavigate();

    return(
        <>
        <section className='sec-gestion-domis'>
            <button className='boton-sec-gestion-domis-volver' onClick={()=>navigate('/caja')}>
                <img src={volver} alt="" />
            </button>
            <Boton imagen={nuevoDomi} className="boton" nombre="Nuevo Domicilio" ruta={`/tomar-pedido/${true}`}/> 
            <Boton imagen={editarDomi} className="boton" nombre="Editar Domicilios" ruta={`/pedidos/${true}`}/> 
        </section>
            
        </>
    )
}

export default GestionDomis