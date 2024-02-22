<template>
    <v-btn text="결제" @click="payHandler()"/>
</template>

<script setup>
import { Bootpay } from '@bootpay/client-js'
import { defineProps } from 'vue';
import axios from '@axios'

const props = defineProps({
    pay: {
        type: Number,
        default: null
    }
});
const payHandler = async () => {    //await과 함께 사용하는 함수
    let response; // response 변수 선언
    try {
        response = await Bootpay.requestPayment({
            "application_id":"회원 가입하고 받은 키",  
            "price": props.pay,
            "order_name": "HealthyReal 챌린지",
            "order_id": "TEST_ORDER_ID",
            // "pg": "카카오페이",
            // "method": "간편",
            "tax_free": 0,
            "user": {
                "id": "OSH",
                "username": "김길동",
                "phone": "01000000000",
                "email": "test@test.com"
            },
            "items": [
                {
                "id": "item_id",
                "name": "챌린지 참여비",
                "qty": 1,
                "price": props.pay
                }
            ],
            "extra": {
                "open_type": "iframe",
                "card_quota": "0,2,3",
                "escrow": false
            },
        // }).error(function (data) { 
        //     // 결제가 실패했을 때 호출되는 함수입니다.
        //     var msg = "결제 에러입니다.: " + JSON.stringify(data);
        //     alert(msg);
        //     console.log(data);
        // }).cancel(function (data) {
        //     // 결제창에서 결제 진행을 하다가 취소버튼을 눌렀을때 호출되는 함수입니다.
        //     var msg = "결제 취소입니다.: " + JSON.stringify(data);
        //     alert(msg);
        //     console.log(data);
        // }).confirm(function (data) {
        //     // 결제가 진행되고 나서 승인 이전에 호출되는 함수입니다.
        //     // 일부 결제는 이 함수가 호출되지 않을 수 있습니다. ex) 가상계좌 및 카드 수기결제는 호출되지 않습니다.        
        //     // 만약 이 함수를 정의하지 않으면 바로 결제 승인이 일어납니다.
        //     if (confirm('결제를 정말 승인할까요?')) {
        //         console.log("do confirm data: " + JSON.stringify(data));
        //         // 이 함수를 반드시 실행해야 결제가 완전히 끝납니다.
        //         // 부트페이로 서버로 결제를 승인함을 보내는 함수입니다.
        //         this.transactionConfirm(data);
        //     } else {
        //         var msg = "결제가 승인거절되었습니다.: " + JSON.stringify(data);
        //         alert(msg);
        //         console.log(data);
        //     }
        // }).done(function (data) {
        //     // 결제가 모두 완료되었을 때 호출되는 함수입니다.
        //     alert("결제가 완료되었습니다.");
        //     console.log(data);
        // }).ready(function (data) {
        //     // 가상계좌 번호가 체번(발급) 되었을 때 호출되는 함수입니다.
        //     console.log(data);        
        });
        paycheck(response);
    } catch (error) {
        console.log(error.message); // 오류 처리
    }
}

function paycheck(response){
    console.log(response);
    const status_locale = response.data.status_locale;
    const pay_name = response.data.order_name;
    const pay_price = response.data.price;
    const pay_date = response.data.purchased_at;
    const pay_method = response.data.pg;
    // 결제 완료 후의 동작 추가
    if (status_locale === '결제완료') {
        // 결제가 성공적으로 완료된 경우
        alert('결제가 완료되었습니다.');
        payListInsert('OSH', 1, pay_name, pay_price, pay_method);
    } else {
        // 결제가 실패한 경우
        alert('결제에 실패하였습니다.');
    }
}

const payListInsert = async (id, pay_type, pay_name, pay_price, pay_method) =>{
    let formData = new FormData()
    formData.append('id', id)
    formData.append('pay_type', pay_type)
    formData.append('pay_name', pay_name)
    formData.append('pay_price', pay_price)
    formData.append('pay_method', pay_method)

    await axios.post('http://localhost:8080/Payment/Write.do', formData, { 
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        console.log('성공')
        console.log(response.data)       
    }).catch(error => {
        console.log('실패')
    })    
}

</script>



