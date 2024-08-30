import InfoPanier from '../Panier'
import { Link, Outlet, useLocation } from 'react-router-dom'
import { useAppSelector } from '../../main'
import { getProductCountInCart } from '../../store/reducers/selectors'
import UserStatus from './UserStatus'
import { NavLink } from 'react-router-dom'

const LayoutRouter = () => {

  //const { client: ClientState, connecter: (c: Client) => void, seDeconnecter: () => void} = useLoaderData();
  const taillePanier = useAppSelector(getProductCountInCart)

  const { pathname } = useLocation()

  const links = [
    ["/produits", "Produits"],
    ["/", "Commandes"],
    ["/", "Entrepots"],
  ]

  return (
    <>
      <div className="row" style={{height: "100%"}}>
        <div className="col-3 layout">
          <div className='site-logo'><Link to="/">Bouteek</Link></div>
          <hr />
          <ul className='nav flex-column'>
            {links.map((link, i) => (
              <li key={i} className='nav-item'><NavLink className={"nav-link navlink " + (pathname === link[0] ? "active" : "")} to={link[0]}>{link[1]}</NavLink></li>
            ))}
          </ul>
          <hr/>
          <UserStatus />
          <hr/>
          <InfoPanier taillePanier={taillePanier} />
        </div>
        <div className="col-9 content"><Outlet /></div>
      </div>

    </>
  )
}



export default LayoutRouter