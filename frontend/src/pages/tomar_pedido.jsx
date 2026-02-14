import './tomar_pedido.css'
import arrow from '../assets/flecha.png'
import FilaTomarPedido from '../components/fila_tomar_pedido'
import { useEffect, useState } from 'react'
import { apiRequest } from '../services/api'
import { useNavigate, useParams } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'
import { formateador } from './ver_ventas'
import { toast } from 'react-toastify';
const TomarPedido = () => {

    const { id, mesa } = useParams();
    const token = localStorage.getItem("token")
    const decode = jwtDecode(token);
    const nombre = decode.sub
    const navigate = useNavigate()

    



    const [visible, setVisible] = useState(false)

    const [digitado, setDigitado] = useState("")
    const [productos, setProductos] = useState([])
    const [pedido, setPedido] = useState([])
    const [total, setTotal] = useState(0)
    const [mesaPedido, setMesaPedido] = useState(id != undefined ? mesa : 0)

    useEffect(() => {
        if (!id) return;

        apiRequest(`/api/detallePedido/${id}`, {
            metodo: "GET"
        }).then(data => {
            setPedido(data)
        })


    }, [id])


    const actDigitado = (e) => {
        setDigitado(e.target.value)
    }

    const buscarProductos = async () => {
        return apiRequest(`/api/producto/listar/${digitado}`, {
            metodo: "GET"
        })
    }

    const anularPedido = async () => {
        return apiRequest(`/api/pedidos/actualizar/${id}/CANCELADO`, {
            metodo: "PUT"
        })
    }

    useEffect(() => {
        const traerProductos = async () => {
            if (digitado != "") {
                const tmp = await buscarProductos()
                setProductos(tmp)

            }
        }
        traerProductos()

    }, [digitado])

    const elegirProducto = (producto) => {
        const existe = pedido.some(p => p.nombreProducto === producto.nombreProducto);
        if (!existe) {
            setPedido(pedido => [...pedido, {
                indice: producto.id,
                nombreProducto: producto.nombreProducto,
                cantidadProducto: 1,
                precioMomento: producto.precio,
                subtotalPedido: producto.precio * 1
            }])
            setVisible(false)
        }

    }

    const funcionDatosHijo = (cantidad, index, subtotal) => {

        setPedido(tmp => (
            tmp
                .map(i =>

                    i.nombreProducto === index ?
                        { ...i, cantidadProducto: cantidad, subtotalPedido: subtotal }
                        : i

                )

                .filter(t => t.cantidadProducto > 0)
        ))

    }

    useEffect(() => {

        const calcularTotal = () => {
            const suma = pedido.reduce(
                (acum, p) => acum + p.subtotalPedido,
                0
            )
            setTotal(suma)
        }
        calcularTotal()
    }, [pedido])


    const confirmarPedido = async () => {
        return apiRequest(`/api/pedidos/crear/${nombre}`, {
            metodo: "POST",
            body: {
                numeroMesa: mesaPedido,
                total: total,
                productos: pedido.map(p => ({
                    nombreProducto: p.nombreProducto,
                    cantidadProducto: p.cantidadProducto,
                    subtotalPedido: p.subtotalPedido,
                    precioMomento: p.precioMomento
                }
                )
                )
            }
        })
    }

    const actualizarPedido = async () => {
        return apiRequest(`/api/pedidos/actualizar/${id}`, {
            metodo: "PUT",
            body: {
                numeroMesa: mesaPedido,
                productos: pedido.map(p => ({
                    nombreProducto: p.nombreProducto,
                    cantidadProducto: p.cantidadProducto,
                    subtotalPedido: p.subtotalPedido,
                    precioMomento: p.precioMomento
                }
                )
                )
            }
        })
    }

    const subirPedido = async () => {
        if (pedido.length!=0) {            
            if (id != undefined) {
            await actualizarPedido()
                navigate("/mesera");    
                toast.success("¡Pedido actualizado con éxito!");
        } else {
            try {
                await confirmarPedido()
                navigate("/mesera");    
                toast.success("¡Pedido confirmado con éxito!");
            } catch (error) {
                toast.error(`${error.message }`);
            }
        }
        }

        
    }

    const cambiarMesa = (e) => {
        const valorOriginal = e.target.value;
    
    if (valorOriginal.trim() === '' || isNaN(valorOriginal)) {
        setMesaPedido(0);
        toast.error("¡Número de mesa inválido!", { toastId: "error-mesa" });
        return;
    }

    const numMesa = parseInt(valorOriginal, 10);

    if (numMesa <= 0 || numMesa > 100) {
        setMesaPedido(0);
        toast.error("Rango de mesa inválido (1-100)", { toastId: "error-mesa" });
        return;
    }
    setMesaPedido(numMesa);
    }

    const cancelarPedido = async () => {
        const tmp = await anularPedido();
        navigate("/mesera");
    }

    return (
        <>
            <section className='tomar-pedido-sec'>
                {
                    visible && (
                        <div className='selector'>
                            <h1>Búsqueda</h1>
                            <form  >
                                <input onChange={actDigitado} type="text" placeholder='Ingrese un dato de búsqueda' className='input-selector' />
                                <div className='resultados'>
                                    {productos != null ? (
                                        productos.map((p, index) => {
                                            return (
                                                <div onClick={() => { elegirProducto(p) }} key={p.id} className='resultado'>{p.nombreProducto}</div>
                                            )


                                        })
                                    ) : (
                                        <p>No hay</p>
                                    )

                                    }

                                </div>
                            </form>
                            <button onClick={() => {
                                setVisible(false)
                            }} className='boton-busqueda'>Aceptar</button>
                        </div>
                    )
                }

                <div className='main'>
                    <div className='tomar-pedido-div-uno'>
                        <button className='tomar-pedido-boton-volver' onClick={() => navigate("/mesera")}>
                            <img src={arrow} alt="" />
                        </button>
                    </div>
                    <div className='tomar-pedido-div-dos'>
                        <label htmlFor="" className='numero-mesa'>Número de mesa</label>
                        {
                            id != undefined ? (

                                <input type="text" className='input-mesa' onChange={cambiarMesa} value={mesaPedido} />
                            ) : (
                                <input type="text" placeholder="Ingrese un número de mesa" className='input-mesa' onChange={cambiarMesa} />
                            )
                        }

                    </div>
                    <div className='tomar-pedido-div-tres'>

                        <div>
                            <h3>Pedido</h3>
                        </div>

                        <div className='items-pedido'>

                            {
                                pedido.map((p, index) => (
                                    <FilaTomarPedido key={p.nombreProducto} nombre_producto={p.nombreProducto.charAt(0).toUpperCase() + p.nombreProducto.slice(1)} funcion={funcionDatosHijo} index={p.nombreProducto} precio={p.precioMomento} cantidad={p.cantidadProducto} />
                                ))
                            }
                        </div>

                    </div>
                    <div className='tomar-pedido-div-cuatro'>
                        <button onClick={() => {
                            setVisible(true)
                        }}>Añadir producto</button>
                    </div>
                </div>

                <footer className='tomar-pedido-div-cinco'>
                    <div className='div-confirmar-pedido'>

                        {id != undefined ? (
                            <>
                                <button className='button-confirmar-pedido' disabled={pedido.length === 0 || mesaPedido ==0} onClick={subirPedido}>Confirmar pedido</button>
                                <button className='button-anular-pedido' onClick={cancelarPedido}>Anular pedido</button>
                            </>

                        ) : (
                            <button className='button-confirmar-pedido' disabled={pedido.length === 0 || mesaPedido ==0} onClick={subirPedido}>Confirmar pedido</button>
                        )

                        }

                    </div>

                    <h3>Total</h3>
                    <h3>{formateador.format(total)}</h3>
                </footer>
            </section>

        </>
    )
}

export default TomarPedido