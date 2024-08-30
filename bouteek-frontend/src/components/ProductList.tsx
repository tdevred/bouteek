import { useState } from 'react'
import { Product } from './types/products'

type ProductListProps = {
    products: Product[],
    acheter: (i: Product) => void 
}

const ProductList = ({ products, acheter }: ProductListProps) => {
    const [search, setSearch] = useState<string>("");
    const [searchEntered, setSearchEntered] = useState<string>("");

    return (
        <>
            <div className='row'>
                <h1 className='col-7'>Produits</h1>
                <div className="col-5 row">
                    <input className='col' type="text" onChange={(e) => setSearchEntered(e.target.value.toLowerCase())} />
                    <button className='col-2' onClick={() => setSearch(searchEntered)}>Rechercher</button>
                    {search && <button className='col-2' onClick={() => setSearch("")}>Effacer</button>}
                </div>
            </div>
            {search && <h1>{search}</h1>}
            <ul>
                {products.filter(p => p.name.toLowerCase().includes(search)).map((product, i) => (
                    <div key={i} className="card" style={{ width: "18rem" }}>
                        <div className="card-body">
                            <h5 className="card-title">{product.name}</h5>
                            <p className="card-text">{product.description}</p>
                            <a href="#" className="btn btn-primary" onClick={() => acheter(product)}>Acheter</a>
                        </div>
                    </div>
                ))}
            </ul>
        </>
    )
}

export default ProductList