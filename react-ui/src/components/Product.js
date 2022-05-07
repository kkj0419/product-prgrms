import React from "react";

export function Product(props) {
    const productId = props.productId;
    const productName =props.productName;
    const productStatus=props.productStatus;
    const stock = props.stock;
    const price=props.price;

    const handleAddBtnClicked=e =>{
        props.onAddClick(productId);
    }

    return (
        <>
            <div className="col-2"><img className="img-fluid" id="productImage" src="https://cdn-icons-png.flaticon.com/512/7035/7035106.png" alt=""/>
            </div>
            <div className="col">
                <div className="row" id="productName" >{productName}</div>
                <div className="row">{productStatus}</div>
                <div className="row" >{stock}개</div>
            </div>
            <div className="col text-center price">{price}원</div>
            <div className="col text-end action">
                <button onClick={handleAddBtnClicked} className="btn btn-small btn-outline-dark">추가</button>
            </div>
        </>
    )
}