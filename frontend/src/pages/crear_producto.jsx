import { useEffect, useState } from 'react'
import { apiRequest } from '../services/api'
import './crear_producto.css'
import { useNavigate, useParams } from 'react-router-dom'

const CrearProducto = () => {
    const { id, nombre, precio } = useParams()
    const navigate=useNavigate();


    const enviarProducto = async (cuerpo) => {
        return apiRequest("/api/producto/crear", {
            metodo: "POST",
            body: cuerpo
        })
    }

    const actualizarProducto = async (cuerpo) => {
        return apiRequest(`/api/producto/actualizar/${id}`, {
            metodo: "PUT",
            body: cuerpo
        })
    }

    const capturarDatos = async (e) => {
        e.preventDefault();
        const nombreProducto = e.target.nombrePizza.value;
        const precioProducto = e.target.precioPizza.value;
        const combinable = e.target.estado.value;

        const cuerpo = {
            nombreProducto: nombreProducto,
            precioProducto: precioProducto,
            combinable: combinable,
            activo: true
        };

        if (id != undefined) {
            const actualizar = await actualizarProducto(cuerpo);
        } else {
            const enviar = await enviarProducto(cuerpo);
        }
        navigate("/gestion-productos");

    }



    return (
        <>
            <section className='crear-producto-sec'>
                <form onSubmit={capturarDatos} className='form-crear-producto'>
                    <div><h1 className='titulo-nuevo-producto'>Nuevo Producto</h1></div>
                    <div className='crear-producto-inputs'>
                        <label htmlFor="" className='labels-nuevo-producto'>Nombre del producto</label>
                        {id != undefined ? (
                            <input type="text" className='inputs' name='nombrePizza' defaultValue={nombre} />
                        ) : (
                            <input type="text" className='inputs' name='nombrePizza' />
                        )

                        }
                    </div>
                    <div className='crear-producto-inputs'>
                        <label htmlFor="" className='labels-nuevo-producto'>Precio del producto</label>
                        {id != undefined ? (
                            <input type="text" className='inputs' name='precioPizza' defaultValue={precio} />
                        ) : (
                            <input type="text" className='inputs' name='precioPizza' />
                        )

                        }

                    </div>
                    <div>
                        <select name="estado" id="" className='estado'>
                            <option value="true">Combinable</option>
                            <option value="false">Completa</option>
                        </select>
                    </div>
                    <button className='boton-guardar-crear-producto'>Guardar</button>
                </form>

            </section>
        </>
    )
}

export default CrearProducto