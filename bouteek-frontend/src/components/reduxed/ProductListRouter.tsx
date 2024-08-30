import { useState } from 'react';
import { useAppDispatch, useAppSelector } from '../../main';
import { addToCart } from '../../store/reducers/cart/slice';

type ProductListProps = {
    pageSize: number
}

const ProductListRouter = ({pageSize}: ProductListProps) => {
    const dispatch = useAppDispatch();
    const products = useAppSelector(state => state.products.products)

    const [search, setSearch] = useState<string>("");
    const [searchEntered, setSearchEntered] = useState<string>("");
    const [currentPage, setCurrentPage] = useState<number>(1);

    const maxPage = useAppSelector(state => Math.ceil(state.products.products.length / pageSize))

    // pageActuelle + 2
    // skip to start
    // skip to end
    
    const leftMax = Math.max(currentPage - 2, 1)
    const rightMax = Math.min(currentPage + 2, maxPage)
    const windowSize = rightMax - leftMax + 1;

    const pageRange = [...Array(windowSize).keys()].map(v => v + leftMax);

    const productsInRange = products.slice(pageSize * (currentPage-1), pageSize*currentPage)

    const setPreviousPage = () => {
        if(currentPage > 1) {
            setCurrentPage(currentPage-1)
        }
    }

    const navigateToPage = (n: number) => {
        setCurrentPage(n)
    }

    const setNextPage = () => {
        if(currentPage < maxPage) {
            setCurrentPage(currentPage+1)
        }
    }

    return (
        <>
            <div>
                <h1 className='page-title'>Produits</h1>
                <div >
                    <form onSubmit={(e) => { e.preventDefault(); setSearch(searchEntered) }}>
                        <div className='input-group'>
                            <input className='form-control' type="text" onChange={(e) => setSearchEntered(e.target.value.toLowerCase())} />
                            <div className='input-group-append'>
                                <button className='btn btn-outline-secondary' onClick={() => setSearch(searchEntered)}>Rechercher</button>
                                {search && <button className='btn btn-outline-secondary' onClick={() => setSearch("")}>Effacer</button>}
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            {search && <h1>{search}</h1>}
            <nav aria-label="Page navigation example" style={{marginTop: "20px", marginBottom: "20px"}}>
                <ul className="pagination">
                    <li className="page-item"><button onClick={() => navigateToPage(1)} disabled={currentPage === 1 ? true : false} className={"page-link " + (currentPage === 1 ? "pagination-disabled" : "")}>Start</button></li>
                    <li className="page-item"><button onClick={() => setPreviousPage()} disabled={currentPage === 1 ? true : false} className={"page-link " + (currentPage === 1 ? "pagination-disabled" : "")}>Previous</button></li>
                    {pageRange.map((v, i) => {
                        return <li key={i} className={"page-item " + (currentPage === v ? "active" : "")}><button onClick={() => navigateToPage(v)} className="page-link">{v}</button></li>
                    })}
                    <li className="page-item"><button onClick={() => setNextPage()} disabled={currentPage === maxPage} className={"page-link " + (currentPage === maxPage ? "pagination-disabled" : "")}>Next</button></li>
                    <li className="page-item"><button onClick={() => navigateToPage(maxPage)} disabled={currentPage === maxPage} className={"page-link " + (currentPage === maxPage ? "pagination-disabled" : "")}>End</button></li>
                </ul>
            </nav>
            <div className='row'>
                {productsInRange.filter(p => p.name.toLowerCase().includes(search)).map((product, i) => (
                    <div key={i} className='col-6'>
                        <div className="card w-75"  style={{ height: "19rem", margin: "10px" }}>
                            <h5 className='card-header'>{product.category}</h5>
                            <div className="card-body">
                                <h5 className="card-title">{product.name}</h5>
                                <p className="card-text">{product.description}</p>
                                <p className="card-text">{product.price} €</p>
                                <a href="#" className="btn btn-outline-primary" onClick={() => dispatch(addToCart(product.id))}>Acheter</a>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </>
    )
}

export default ProductListRouter