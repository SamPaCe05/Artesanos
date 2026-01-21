import './gestion_productos.css'
import Boton from '../components/boton_gestion'
import nuevo from '../assets/nuevo-producto.png'
import lupa from '../assets/lupa.png'

const GestionProductos = () => {
    return (
        <>
            <section className='gestion_sec'>
                <Boton imagen={nuevo} className={"boton"} nombre="Crear producto"/>
                <Boton imagen={lupa} className={"boton"} nombre="Buscar producto"/>
            </section>
        </>
    )
}

export default GestionProductos