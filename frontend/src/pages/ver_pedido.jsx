import './ver_pedido.css'
import FilaPedido from '../components/fila_pedido'
import { useNavigate, useParams } from 'react-router-dom'
import { apiRequest } from '../services/api';
import { useEffect, useRef, useState } from 'react';
import { useReactToPrint } from "react-to-print";
import Comanda from './comanda';
import { formateador } from './ver_ventas';

const VerPedido = ({ n_pedido, n_mesa }) => {
    const [pedido, setPedido] = useState([])
    const [cantidad, setCantidad] = useState(0)
    const { id, mesa, estado } = useParams();
    const [total, setTotal] = useState(0)
    const comandaRef = useRef();
    const navigate=useNavigate();

    const pedidoPorId = async () => {
        return apiRequest(`/api/detallePedido/${id}`, {
            metodo: "GET" 
        })
    }

    const confirmarPedido = async () => {
        return apiRequest(`/api/pedidos/actualizar/${id}/RESUELTO`, {
            metodo: "PUT"
        })
    }

    const anularPedido = async () => {
        return apiRequest(`/api/pedidos/actualizar/${id}/CANCELADO`, {
            metodo: "PUT"
        })
    }

    const cancelarPedido=async()=>{
        const tmp=await anularPedido();
        navigate("/pedidos");
    }

    const resolverPedido = async () => {
        const tmp = await confirmarPedido()
        navigate("/pedidos");   
    }
    useEffect(() => {
        const traerPedido = async () => {
            const tmp = await pedidoPorId()
            setCantidad(tmp.length)
            setPedido(tmp)
            const suma=tmp.reduce((cnt,p)=>{
                return cnt+p.subtotalPedido
            },0)
            setTotal(suma)
        }
        traerPedido()
    }, [])

    const imprimir=useReactToPrint({
        contentRef:comandaRef
    });



    return (
        <>
            <section className='fila-pedido-sec'>
                <div className='fila-pedido-div-uno'>
                    <div className='fila-pedido-text-pedido'>
                        <h3>Pedido {id}</h3>
                    </div>
                    <div className='fila-pedido-text-mesa'>
                        <h3>Mesa N.{mesa}</h3>
                    </div>
                </div>
                <div className='fila-pedido-div-dos'>
                    <div className='fila-pedido-index'>
                        <h3>Productos</h3>
                        <h3>Cantidad</h3>
                        <h3>Precio</h3>
                        <h3>Total</h3>
                    </div>
                    <div className='filas_ver'>

                        {
                            pedido.map((p, index) => (
                                <FilaPedido key={index} nombre={p.nombreProducto.charAt(0).toUpperCase() + p.nombreProducto.slice(1)} cantidad={p.cantidadProducto} precio={formateador.format(p.precioMomento)} subtotal={formateador.format(p.subtotalPedido)} />
                            ))
                       
                            
                        }

                    </div>
                    <div className='fila-pedido-total'>
                        <h3>Total</h3>
                        <div>{formateador.format(total)}</div>
                    </div>
                </div>
                <div className='fila-pedido-div-tres'>
                    {estado != undefined ? (
                        <>
                            

                            <div>
                                <button className='fila-boton-dos' onClick={imprimir}>Imprimir Comanda</button>
                            </div>
                        </>

                    ) : (
                        <>
                            <div>
                                <button className='fila-boton-uno' onClick={cancelarPedido}>Anular Pedido</button>
                            </div>
                            <div>
                                <button className='fila-boton-dos' onClick={resolverPedido}>Confirmar Pago</button>
                            </div>
                            <div>
                                <button className='fila-boton-dos' onClick={imprimir}>Imprimir Comanda</button>
                            </div>
                        </>
                    )

                    }

                </div>
            </section>
            <div className='comanda'>
                    <Comanda ref={comandaRef} pedido={pedido} id={id} mesa={mesa} total={total}/>
            </div>
        </>
    )
}

export default VerPedido