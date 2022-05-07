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

    const handleAddClicked = productId => {
        const product = products.find(v=> v.productId === productId);
        const found =items.find(v => v.productId === productId);
        const updatedItems=
            found ? items.map(v => (v.productId ===productId) ? {...v, count:v.count+1} : v) :
        [...items, {
            ...product,
            count:1
        }]
        setItems(updatedItems);
    };

    const handleOrderSubmit = (order) => {
        if (items.length === 0) {
            alert("아이템을 추가해 주세요!");
        } else {
            axios.post('http://localhost:8080/api/v1/orders', {
                email: order.email,
                address: order.address,
                items: items.map(v => ({
                    productId: v.productId,
                    price: v.price,
                    quantity: v.count
                }))
            }).then(
                v => alert("주문이 정상적으로 접수되었습니다."),
                e => {
                    alert("서버 장애");
                    console.error(e);
                })
        }
    };

    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Shopping</h1>
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
            <a id="sourceLink" href="https://www.flaticon.com/kr/free-icons/" title="시장 아이콘">시장 아이콘  제작자: Corner Pixel - Flaticon</a>
        </div>
    );
}

export default App;
