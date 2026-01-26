import './buscar_producto.css'
import FilaProducto from '../components/fila_producto'
import MenuBuscar from '../components/menu_buscar'

const BuscarProducto = ({n}) => {

   

    return (
        <>
            <section className='buscar-producto-section'>
                <h2>Buscar Producto</h2>
                <input type="text" placeholder='Ingrese un producto' />
                <div className='busqueda-div'>
                    <FilaProducto campoUno="ID" campoDos="Nombre" campoTres="Precio" campoCuatro="Gestionar" />
                    <div className='filas-productos'> 
                        <FilaProducto campoUno="21" campoDos="Pizza Espanta Brujas" campoTres="21.900" campoCuatro={<MenuBuscar />} />
                        <FilaProducto campoUno="23" campoDos="Pizza Campesina" campoTres="21.900" campoCuatro={<MenuBuscar />} />
                        <FilaProducto campoUno="24" campoDos="Pizza Pecosa" campoTres="21.900" campoCuatro={<MenuBuscar />} />
                        {
                            Array.from({ length: n }).map((_, index) => (
                                <FilaProducto/>
                            ))


                        }
                    </div>
                </div>
            </section>
        </>
    )
}

export default BuscarProducto