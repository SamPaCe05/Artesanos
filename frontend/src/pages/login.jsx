import { useState } from 'react'; // Añadido para feedback visual
import './login.css';
import loginImg from "../assets/artesanos_logo.jpg";
import { autenticar } from '../services/autenticacion';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
const Login = () => {
    const navigate = useNavigate();
    const [cargando, setCargando] = useState(false);

    const loguearse = async (e) => {
        e.preventDefault();
        setCargando(true); 

        const data = new FormData(e.target);
        const body = {
            nombreUsuario: data.get("nombreUsuario").trim(),
            contrasena: data.get("contrasena").trim()
        };

        try {
            const res = await autenticar(body);
            
            if (!res || !res.token) {
                throw new Error("No se recibió un token válido.");
            }
            toast.success("¡Bienvenido de nuevo!");

            localStorage.setItem("token", res.token);
            localStorage.setItem("rol", res.rol || "");

            const destino = res.rol === "ROLE_CAJA" ? "/caja" 
                          : res.rol === "ROLE_MESERA" ? "/mesera" 
                          : "/";

            navigate(destino);

        } catch (error) {
        } finally {
            setCargando(false);
        }
    };

    return (
        <section className="sec">
            <form onSubmit={loguearse} className='form-login'>
                <img src={loginImg} alt="Login" />
                <h3>Iniciar Sesión</h3>
                
                <div className='user-contain'>
                    <label className='lbl'>Nombre de usuario</label>
                    <input 
                        type="text" 
                        name="nombreUsuario" 
                        required 
                        placeholder="Ingrese su nombre" 
                        className='user' 
                    />
                </div>

                <div className='passw-contain'>
                    <label className='lbl'>Contraseña</label>
                    <input 
                        type="password" 
                        name="contrasena" 
                        required 
                        placeholder='Ingrese su contraseña' 
                        className='passw' 
                    />
                </div>

                <div className='contain-iniciar'>
                    <button 
                        className='iniciar' 
                        type='submit' 
                        disabled={cargando}
                    >
                        {cargando ? "Cargando..." : "Iniciar"}
                    </button>
                </div>
            </form>
        </section>
    );
};

export default Login;