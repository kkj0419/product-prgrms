import './App.css';

import 'bootstrap/dist/css/bootstrap.css'
import React, {useEffect, useState} from "react";
import axios from "axios";
import {ProductList} from "./components/ProductList";
import {Summary} from "./components/Summary";

function App() {

    const [products, setProducts] = useState([]);
    const [items, setItems] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/products')
            .then(v => setProducts(v.data));
    }, []);

    const handleAddClicked = () => {

    };

    const handleOrderSubmit = () => {

    };

    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Grids & Circle</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <ProductList products={products} onAddClick={handleAddClicked}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <Summary items={items} onOrderSubmit={handleOrderSubmit}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
