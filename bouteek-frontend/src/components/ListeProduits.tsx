import { useAppDispatch, useAppSelector } from "../main";
import { addToCart, deleteFromCart, validateCart } from "../store/reducers/cart/slice";
import { Product } from "./types/products";

type ListeProduitsProps = {
  panier: [Product, number][]
}

const ListeProduits = ({ panier }: ListeProduitsProps) => {
  const dispatch = useAppDispatch();

  const user_token = useAppSelector(state => state.user.token)

  const coutPanier = panier.map(v => v[0].price * v[1]).reduce((a, b) => a+b, 0)

  return (
    <>
      {panier.length === 0 && <p>Votre panier est vide</p>}
      <ul className="list-group">
        {panier.map((item, i) => (
          <li key={i} className="list-group-item d-flex justify-content-between align-items-center row">
            <div className="col-7">
              {item[0].name}
            </div>
            <div className="col">
              <button className="btn btn-secondary" onClick={() => dispatch(deleteFromCart(item[0].id))}>-</button>
              <span className="badge text-bg-primary">{item[1]}</span>
              <button className="btn btn-secondary" onClick={() => dispatch(addToCart(item[0].id))}>+</button>
            </div>
            <div className="col-2">
            {item[0].price * item[1]}€
            </div>
          </li>
        ))}
      </ul >
      {panier.length > 0 && <p>Total panier : {coutPanier}€</p>}
      {panier.length > 0 && <button onClick={() => {
        const data = panier.map(v => [v[0].id, v[1]] as [number, number]) as [number, number][];
        console.log("data is", data)
        dispatch(validateCart({cart: data, user_token: user_token!}))
      }} className="btn btn-primary">Valider panier</button>}
    </>
  )
}

export default ListeProduits