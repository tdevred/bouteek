import { useAppDispatch, useAppSelector } from '../../main'
import { disconnect } from '../../store/reducers/user/slice';
import ClientCard from '../ClientCard';
import { NavLink } from 'react-router-dom';

const UserStatus = () => {
    const user = useAppSelector(state => state.user);
    const dispatch = useAppDispatch();

    return <div className='connection-status'>
        
        {user.isLoggedIn && <ClientCard username={user.username!} disconnect={() => dispatch(disconnect())} />}
        {!user.isLoggedIn && <><p>Vous n'êtes pas connecté.</p><div><NavLink className='btn btn-primary' to='/login'>Se connecter <i className="bi bi-person"></i></NavLink></div></>}
    </div>
}

export default UserStatus