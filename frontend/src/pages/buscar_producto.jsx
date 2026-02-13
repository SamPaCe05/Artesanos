import './buscar_producto.css'
import FilaProducto from '../components/fila_producto'
import MenuBuscar from '../components/menu_buscar'
import { useEffect, useState } from 'react'
import { apiRequest } from '../services/api'
import { formateador } from './ver_ventas';
import arrow from '../assets/flecha.png'
import { useNavigate } from 'react-router-dom'



const BuscarProducto = ({ n }) => {
    const [dato, setDato] = useState("")
    const [pizzas, setPizzas] = useState([])
    const filtrar = (e) => {
        setDato(e.target.value)
    }
    const navigate = useNavigate();

    const filtrarProducto = async () => {
        return apiRequest(`/api/producto/listar/${dato}`, {
            metodo: "GET"
        })
    }

    useEffect(() => {
        const tmpfunc = async () => {
            if (dato != "") {
                const tmp = await filtrarProducto()
                setPizzas(tmp)
            }

        }

        tmpfunc()
    },

        [dato])


    return (
        <>
            <section className='buscar-producto-section'>
                <div className='title-buscar-producto'>
                    <button onClick={() => navigate('/gestion-productos')}><img src={arrow} alt="" /></button>
                    <h2>Buscar Producto</h2>
                </div>

                <form onChange={filtrar}>
                    <input type="text" placeholder='Ingrese un producto' name='buscador' className='input-buscar-producto' />
                </form>

                <div className='busqueda-div'>
                    <FilaProducto campoUno="ID" campoDos="Nombre" campoTres="Precio" campoCuatro="Gestionar" />
                    <div className='filas-productos'>

                        {
                            pizzas != null ? (
                                pizzas.map((p, index) => (
                                    <FilaProducto key={index} campoUno={p.id} campoDos={p.nombreProducto.charAt(0).toUpperCase() + p.nombreProducto.slice(1)} campoTres={formateador.format(p.precio)} campoCuatro={<MenuBuscar id={p.id} nombre={p.nombreProducto} precio={p.precio} />} />
                                ))
                            ) : (
                                <p>No hay productos que coincidan con la busqueda</p>
                            )





                        }
                    </div>
                </div>
            </section>
        </>
    )
}

export default BuscarProducto