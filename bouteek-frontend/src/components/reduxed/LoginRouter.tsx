import { useState } from 'react'
import { useAppDispatch, useAppSelector } from '../../main'
import { connect } from '../../store/reducers/user/slice';
import { useNavigate } from 'react-router-dom';

const Login = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    const isConnected = useAppSelector(state => state.user.isLoggedIn)
    if (isConnected) {
        return <></>
    }

    return (
        <>
            <h1 className='page-title'>Login</h1>
            <div>
                <form className='login-form d-flex flex-column' onSubmit={async (e) => {
                    e.preventDefault();
                    dispatch(connect({ email, password }))
                    setEmail("")
                    setPassword("")
                    navigate("/")
                }}>
                    <div className='form-group p-2'>
                        <label>Email adress</label>
                        <input className='form-control' id="emailInput" value={email} placeholder='Enter email' type="text" onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div className='form-group p-2'>
                        <label>Password</label>
                        <input className='form-control' id="passwordInput" value={password} placeholder='Password' type="password" onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div>
                        <button type="submit" className='btn btn-primary'>Submit</button>
                    </div>
                </form >
            </div>
        </>
    )
}

export default Login