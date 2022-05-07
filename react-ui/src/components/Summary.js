import {useState} from "react";
import {SummaryItem} from "./SummaryItem"

export function Summary({items = [], onOrderSubmit}) {
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0);
    const [order, setOrder] = useState({
        email: "", address: ""
    });

    const handleEmailInputChanged = (e) => setOrder({...order, email: e.target.value})
    const handleAddressInputChanged = (e) => setOrder({...order, address: e.target.value})

    const handleSumbit=(e) => {
        if (order.address === "" || order.email === "") {
            alert("입력값을 확인해주세요!")
        } else {
            onOrderSubmit(order);
        }
    }

    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr/>
            {
                items.map(v =>
                    <SummaryItem key={v.productId} count={v.count} productName={v.productName}/>
                )
            }
            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="email" className="form-control mb-1" id="email" value={order.email}
                           onChange={handleEmailInputChanged}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">주소</label>
                    <input type="text" className="form-control mb-1" id="address" value={order.address}
                           onChange={handleAddressInputChanged}/>
                </div>
                <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
            </form>
            <div className="row pt-2 pb-2 border-top">
                <h5 className="col">총금액</h5>
                <h5 className="col text-end">{totalPrice}원</h5>
            </div>
            <button className="btn btn-dark col-12" onClick={handleSumbit}>결제하기</button>
        </>
    )
}