import './ver_ventas.css'
import BotonPedido from '../components/boton_pedido'
import { apiRequest } from '../services/api'
import { useEffect, useState } from 'react'

const VerVentas = () => {
    const [pedidos, setPedidos] = useState([]);
    const [total, setTotal] = useState(0)

    const traerVentas = async (inicio, fin) => {
        return apiRequest(`/api/pedidos/resueltos/cierre/${inicio}/${fin}`, {
            metodo: "GET"
        })
    }

    const buscarVentas = async (e) => {
        e.preventDefault();
        const fechaInicio = e.target.inicio.value;
        const fechaFin = e.target.fin.value;

        const res = await traerVentas(fechaInicio, fechaFin);

        setPedidos(res)
        console.log(res)

    }

    useEffect(() => {
        const ventasActuales = async () => {
            const hoy = new Date().toISOString().split("T")[0];
            console.log(hoy)
            const res = await traerVentas(hoy, hoy)
            setPedidos(res)
        }
        ventasActuales();
    }, [])

    useEffect(() => {
        const acumular = () => {
            return pedidos.reduce((cnt, p) => {
                return cnt + p.total
            },0)
        }
        const suma = acumular()
        setTotal(suma)
    }, [pedidos])

    return (
        <>
            <section className='sec-ver-ventas'>
                <form onSubmit={buscarVentas}>
                    <h2>Historial Pedidos</h2>
                    <div className='contenedor-fechas'>
                        <div className='div-fechas'>
                            <label htmlFor="">Fecha Inicio</label>
                            <input type="date" name='inicio' className='inputs'/>
                        </div>
                        <div className='div-fechas'>
                            <label htmlFor="">Fecha Fin</label>
                            <input type="date" name='fin'className='inputs' />
                        </div>
                    </div>

                    <div className='pedidos-encontrados'>
                        {
                            pedidos.map(p => (
                                <BotonPedido key={p.id} ruta={`/ver-pedido/${p.id}/${p.numeroMesa}/resuelto`} num_mesa={p.numeroMesa} num_pedido={p.id} />
                            ))
                        }

                    </div>

                    <div className='footer-ver-ventas'>
                        <button type='submit' className='boton-buscar' >Buscar</button>
                        <h2 className='total-cierre'>Total Dia ${total}</h2>
                    </div>

                </form>

            </section>
        </>
    )
}

export default VerVentas