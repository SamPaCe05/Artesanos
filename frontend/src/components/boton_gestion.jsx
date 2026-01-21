import './boton_gestion.css'

const Boton = ({ imagen, nombre, className }) => {
    return (
        <div className={`contenedor ${className}`}>
            <img src={imagen} alt={nombre} />
            <h3>{nombre}</h3>
        </div>
    )
}

export default Boton
