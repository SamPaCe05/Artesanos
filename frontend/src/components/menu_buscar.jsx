import './menu_buscar.css'
import editar from '../assets/editar.png'
import eliminar from '../assets/boton-x.png'

const MenuBuscar=()=>{
    return(
        <>
            <div className='menu'>
                <img src={editar} alt="" className='menu-img'/>
                <img src={eliminar} alt="" className='menu-img'/>
            </div>
        </>
    )
}

export default MenuBuscar