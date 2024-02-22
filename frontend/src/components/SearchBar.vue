<template>
    <div class="gl-cpt-search-big-contain">
        <div class="gl-cpt-search gl-cpt-search-big ">
            <div class="gl-cpt-search_input">
                <input style="text-overflow:ellipsis" placeholder="여행지, 명소, 호텔 등으로 검색" />
            </div>
            <div class="gl-cpt-search_button">
                <div class="gl-cpt-search_button-icon"></div>
            </div>
        </div>
    </div>
</template>
<style scope>
.gl-cpt-search-big-contain {
    max-width: 1160px;
    height: 148px;
    background: #fff;
    border: 1px solid #f0f2f5;
    -webkit-box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    box-shadow: 0 4px 16px 0 rgba(69,88,115,.2);
    margin: 0 auto;
    position: relative;
    z-index: 9;
    padding: 0 24px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-radius: 2px;
}

.gl-cpt-search-big-contain .gl-cpt-search {
    -webkit-transform: translateY(-50%);
    transform: translateY(-50%);
    top: 50%;
    position: relative;
    width: 100%;
    height: 78px;
    font-family: arial,sans-serif;
    margin: auto;
    border: 1px solid #dadfe6;
    -webkit-box-shadow: none;
    box-shadow: none;
    border-radius: 2px;
}

.gl-cpt-search-big-contain .gl-cpt-search .gl-cpt-search_input>input {
    border: none;
    -webkit-box-shadow: none;
    box-shadow: none;
    font-size: 24px;
    width: 100%; 
    height: 100%;
    box-sizing: border-box;
    outline: none; 
}

.gl-cpt-search div{
	box-sizing: border-box;
}

.gl-cpt-search_input{
    width: 100%;
    height: 100%;
    padding-left: 24px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background: #fff;
    padding-right: 78px;
    border: 1px solid rgba(0,0,0,.04);
    -webkit-box-shadow: 0 4px 16px 0 rgba(0,0,0,.1);
    box-shadow: 0 4px 16px 0 rgba(0,0,0,.1);
    font-size: 18px;
    border-radius: 4px;
}

.gl-cpt-search-big-contain .gl-cpt-search .gl-cpt-search_button {
    border-top-left-radius: 0;
    border-top-right-radius: 2px;
    border-bottom-left-radius: 2px;
    border-bottom-right-radius: 0;
    width: 78px;
    background: #3264ff;
}

.gl-cpt-search_button {
    top: 0;
    right: 0;
    position: absolute;
    background: #ff9500;
    width: 112px;
    height: 100%;
    border-radius: 0 4px 4px 0;
}
.gl-cpt-search_button-icon {
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAZlBMVEUAAAD///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////+Vn2moAAAAIXRSTlMA8w5rGwnNxlZABNm1Y/iBJufSvq2mnXQoFHwy3pdOSY7plTuLAAABAklEQVQ4y62S2ZKCMBREE4jIvig7js75/58cMkVlIZZPnrem+4a+qYhvMse/w9ik0Xs3rwoOyu3d9G5bxsvZf0pADlNaNVd2EuX7Wb3b0zHW9jrhnaE6KGarqz0/uIEROu/MCsisbK10Rq5WNVAKHyWhNSqBpzjRw2SuCAj2rpyaCjpxJoO707EIAhsk4iACGQRebnEJ6hxY4ceIEmIj7Gap2/iRBx3ryKhLB43nX276D5YYfaAl332p3A8lMBm53HdZeC8vKoBb+n+fqpForiZhhqiT8v5A0weJfJUYbpmIg4RY1gSN7HVfm/CqbK9s1kVMIhdnguU/JmrZio8skfgGf9q4GqOMmS5bAAAAAElFTkSuQmCC) no-repeat 50%;
    width: 100%;
    height: 100%;
}
</style>