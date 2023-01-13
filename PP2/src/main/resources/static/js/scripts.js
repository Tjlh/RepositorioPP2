const input = document.getElementById('input-numb')
const inputAdd1 = document.getElementById('input-add1')
const inputAdd2 = document.getElementById('input-add2')
const inputAdd3 = document.getElementById('input-add3')

function mudar(btn){
    var id = btn.getAttribute('id')
    var min = input.getAttribute('min')
    var val = input.getAttribute('value')
    var calcStep = (id == 'incrementar') ? parseInt(val)+1 : parseInt(val)-1

    if(calcStep >= min){
        input.setAttribute('value', calcStep)
    } 
}

function mudar1(btn1){
    var id1 = btn1.getAttribute('id')
    var min1 = inputAdd1.getAttribute('min')
    var val1 = inputAdd1.getAttribute('value')
    var calcStep1 = (id1 == 'incrementar-add1') ? parseInt(val1)+1 : parseInt(val1)-1

    if(calcStep1 >= min1){
        inputAdd1.setAttribute('value', calcStep1)
    } 
}

function mudar2(btn2){
    var id2 = btn2.getAttribute('id')
    var min2 = inputAdd2.getAttribute('min')
    var val2 = inputAdd2.getAttribute('value')
    var calcStep2 = (id2 == 'incrementar-add2') ? parseInt(val2)+1 : parseInt(val2)-1

    if(calcStep2 >= min2){
        inputAdd2.setAttribute('value', calcStep2)
    } 
}

function mudar3(btn3){
    var id3 = btn3.getAttribute('id')
    var min3 = inputAdd3.getAttribute('min')
    var val3 = inputAdd3.getAttribute('value')
    var calcStep3 = (id3 == 'incrementar-add3') ? parseInt(val3)+1 : parseInt(val3)-1

    if(calcStep3 >= min3){
        inputAdd3.setAttribute('value', calcStep3)
    } 
}
