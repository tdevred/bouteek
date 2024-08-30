import { NavLink } from "react-router-dom";

type PanierProps = {
    taillePanier: number
}

const InfoPanier = ({ taillePanier }: PanierProps) => {
    return (
        <div className="nav flex-column">
            <div><NavLink className="nav-link navlink" to="/panier">Panier <i className="bi bi-cart"></i></NavLink></div>
            <div style={{padding: "10px"}}>
                {taillePanier > 0 && <p><strong>{taillePanier}</strong> {taillePanier > 1 ? "produits" : "produit"}</p>}
                {taillePanier == 0 && <p>Votre panier est vide.</p>}
            </div>
        </div>
    )
}

export default InfoPanier