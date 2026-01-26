import './login.css'
import loginImg from "../assets/artesanos_logo.jpg"

const Login = () => {
    return (
        <>
            <section className="sec">
                <img src={loginImg} alt="Login" />
                <h3>Iniciar Sesión</h3>
                <div className='user-contain'>
                    <label htmlFor="" className='lbl'>Nombre de usuario</label>
                    <input type="text" name="" placeholder="Ingrese su nombre" className='user'/>
                </div>
                <div className='passw-contain'>
                    <label htmlFor="" className='lbl'>Contraseña</label>
                    <input type="password" name="" placeholder='Ingrese su contraseña' className='passw'/>

                </div>
                <div className='contain-iniciar'>
                    <button className='iniciar'>Iniciar</button>
                </div>


            </section>
        </>
    )
}

export default Login;