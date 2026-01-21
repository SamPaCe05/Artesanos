import './boton_gestion.css'

const Boton=({imagen,nombre})=>{
    return(
        <>
            <div className='contenedor'>
                <img src={imagen} alt="" />
                <h3>{nombre}</h3>
            </div>
        </>
    )
}

export default Boton