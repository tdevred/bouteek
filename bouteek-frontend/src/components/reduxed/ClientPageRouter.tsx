import { useAppSelector } from '../../main';
import ListeProduits from '../ListeProduits';
import { getProductsInCart } from '../../store/reducers/selectors';

const ClientPageRouter = () => {
    const client = useAppSelector(state => state.user);

    return (
        <>
            <h1 className='page-title'>Client</h1>
            <p>Bonjour {client.username!}</p>
            <h2  className='page-subtitle'>Panier</h2>
            <ListeProduits panier={useAppSelector(getProductsInCart)} />
        </>
    )
}

export default ClientPageRouter