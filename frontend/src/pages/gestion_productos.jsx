import './gestion_productos.css'
import Boton from '../components/boton_gestion'
import nuevo from '../assets/nuevo-producto.png'
import lupa from '../assets/lupa.png'

const GestionProductos = () => {
    return (
        <>
            <section className='gestion_sec'>
                <Boton imagen={nuevo} nombre="Crear producto"/>
                <Boton imagen={lupa} nombre="Buscar producto"/>
            </section>
        </>
    )
}

export default GestionProductos