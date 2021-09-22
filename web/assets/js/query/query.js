/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let query = {
    q: function (selector) {
        //a diferencia del qall esta función nos devuelve un elemento unico ya sea mediante su id o una clase etc
        return document.querySelector(selector);
    },
    qall: function (selector) {
        //nos devolvera un arreglo de elementos, ya que que el selector sea un id o una clase, etc
        return document.querySelectorAll(selector);
    },
    id: function (selector) {
        //nos devolvera el elemento con el id que se especifique
        return document.getElementById(selector);
    },
    names: function (selector) {
        //nos devolvera un arreglo de elementos con el nombre que se especifique
        return document.getElementsByName(selector);
    },
    atr: function (selector, atributo) {
        //nos devolvera el attribute de un elemento 
        return selector.getAttribute(atributo);
    },
    create: function (selector) {
        //esta función nos crear cualquier elemento (example: tr,td,div,inpu), de igual manera podemos asignarles atributos
        return document.createElement(selector);
    },
    get: function (config) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4) {
                switch (this.status) {
                    case 200:
                        config.success.call(this, this.responseText);
                        break;
                    default:
                        if (config.fail) {
                            config.fail.call(this);
                        }
                        break;
                }
            }
        };
        config.url = config.url + (config.data == undefined ? "" : "?" + this.params(config.data));
        xhttp.open("GET", config.url, true);
        xhttp.send();
    },
    post: function (config) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4) {
                switch (this.status) {
                    case 200:
                        config.success.call(this, this.responseText);
                        break;
                    default:
                        if (config.fail) {
                            config.fail.call(this);
                        }
                        break;
                }
            }
        };
        xhttp.open("POST", config.url, true);
        if (config.content != undefined) {
            xhttp.setRequestHeader("Content-type", config.content);
        }
        xhttp.send(this.params(config.data));

    },
    ajax: function (config) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4) {
                switch (this.status) {
                    case 200:
                        config.success.call(this, this.responseText);
                        break;
                    default:
                        if (config.fail) {
                            config.fail.call(this);
                        }
                        break;
                }
            }
        };
        xhttp.open(config.method.toUpperCase(), config.url, true);
        if (config.content != undefined) {
            xhttp.setRequestHeader("Content-type", config.content);
        }
        if (config.data == undefined) {
            xhttp.send();
        } else {
            let formData = [];
            if (config.data.nodeName == 'FORM') {
                let elements = config.data.elements;
                for (i = 0; i < elements.length; i++) {
                    field_type = elements[i].type.toLowerCase();
                    switch (field_type) {
                        case "text":
                        case "file":
                        case "date":
                        case "password":
                        case "textarea":
                        case "hidden":
                            let val = encodeURIComponent(elements[i].id) + "=" + encodeURIComponent(elements[i].value);
                            formData.push(val);
                            break;
                        case "radio":
                        case "checkbox":
                            if (elements[i].checked) {
                                let val = encodeURIComponent(elements[i].name) + "=" + encodeURIComponent(elements[i].value);
                                formData.push(val);
                            }
                            break;
                        case "select-one":
                        case "select-multi":
                            if (elements[i].selectedIndex != 0) {
                                let val = encodeURIComponent(elements[i].id) + "=" + encodeURIComponent(elements[i].value);
                                formData.push(val);
                            }
                            break;
                        default:
                            break;
                    }
                }
                xhttp.send(formData.join("&"));
            } else if (config.data instanceof FormData) {
                config.data.forEach((value, key) => {
                    let val = encodeURIComponent(key) + "=" + encodeURIComponent(value);
                    formData.push(val);
                });
                /* console.log(formData);
                 return;*/
                xhttp.send(formData.join("&"));
            } else {
                xhttp.send(this.params(config.data));
            }
        }
    },
    resetForm: function (myFormElement) {
        //nos limpiara el formulario omitiendo no limpiar el token
        let elements = myFormElement.elements;
        for (i = 0; i < elements.length; i++) {
            if (elements[i].name !== "__RequestVerificationToken") {
                field_type = elements[i].type.toLowerCase();
                switch (field_type) {
                    case "text":
                    case "file":
                    case "date":
                    case "password":
                    case "textarea":
                    case "hidden":
                        elements[i].value = "";
                        break;
                    case "radio":
                    case "checkbox":
                        if (elements[i].checked) {
                            elements[i].checked = false;
                        }
                        break;
                    case "select-one":
                    case "select-multi":
                        elements[i].selectedIndex = 0;
                        break;
                    default:
                        break;
                }
            }
        }
    },
    params: function (object) {
        let data = [];
        for (obj in object) {
            let val = encodeURIComponent(obj) + "=" + encodeURIComponent(object[obj]);
            data.push(val);
        }
        return data.join("&");
    },
    toast: function (title, message, icon) {
        /**
         * info: icon = info
         * warning: icon = warning
         * success: icon = success
         * error: icon=error
         */
        $.NotificationApp.send(title, message, "top-right", "rgba(0,0,0,0.2)", icon);
    },
    validate: function (data, type) {
        if (type === "int") {
            if (!isNaN(data) && parseInt(data) > 0 &&
                    data != undefined && data != null && /^([0-9])*$/.test(data)) {
                return false;
            } else {
                return true;
            }
        } else if (type === "string") {
            if (typeof data !== "string" || typeof data == 'undefined' || !data || data.length === 0 || data === "" ||
                    !/[^\s]/.test(data) || /^\s*$/.test(data) || data.replace(/\s/g, "") === "" ||
                    !/\S/.test(data) || data === undefined || data === null) {
                return true;
            } else {
                return false;
            }
        } else if (type === "double") {
            if (!isNaN(data) && parseFloat(data) > 0 && /^\d*(\.\d{1})?\d{1,2}$/.test(data) && data !== undefined &&
                    data !== null) {
                return false;
            } else {
                return true;
            }
        } else if (type === "email") {
            //^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$
            ///\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)/
            if (/^(([^<>()\[\]\\.,;:\s@”]+(\.[^<>()\[\]\\.,;:\s@”]+)*)|(“.+”))@((\[[0–9]{1,3}\.[0–9]{1,3}\.[0–9]{1,3}\.[0–9]{1,3}])|(([a-zA-Z\-0–9]+\.)+[a-zA-Z]{2,}))$/.test(data)) {
                return false;
            } else {
                return true;
            }
        } else if (type === "phone") {
            if (/^\d{4}-\d{4}$/.test(data)) {
                return false;
            } else {
                return true;
            }
        } else if (type === "date") {
            if ((this.trim(data) == "") || (this.trim(data).length != 10) || data === null || data === undefined)
                return true;

            let day = parseInt(data.substr(8, 2), 10);
            let month = parseInt(data.substr(5, 2), 10);
            let year = parseInt(data.substr(0, 4), 10);
            // year
            if (isNaN(year) || (year < 1900))
                return true;
            // month
            if (isNaN(month) || (month < 1) || (month > 12))
                return true;
            // day
            if (isNaN(day) || (day < 1) || (day > 31))
                return true;
            else {
                if ((day == 31) && ((month == 4) || (month == 6) || (month == 9) || (month == 11)))
                    return true;
                let diaMax = 31;
                if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0))
                    diaMax = 29;
                else
                    diaMax = 28;
                if (day > diaMax)
                    return true;
            }
            return false;
        } else if (type === "letters") {
            if (/^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$/g.test(data)) {
                return false;
            } else {
                return true;
            }
        } else if (type === "numbers") {
            if (/^([0-9])*$/.test(data)) {
                return false;
            } else {
                return true;
            }
        } else if (type === "DUI") {
            if (/^\D*\d{8}-\D*\d{1}$/g.test(data)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    },
    isUndefinedNull: function (element) {
        if (element === null || element === undefined) {
            return true;
        } else {
            return false;
        }
    },
    emptyElements: function (form) {
        let status = false;
        let empty = 0,
                j = 0;
        let elements = form.elements;
        if (form.tagName = "FORM") {
            for (i = 0; i < elements.length; i++) {
                if (elements[i].name !== "" && elements[i].id !== "") {
                    field_type = elements[i].type.toLowerCase();
                    switch (field_type) {
                        case "text":
                        case "file":
                        case "date":
                        case "password":
                        case "textarea":
                        case "hidden":
                        case "number":
                            if (elements[i].value == "" || elements[i].value == " ") {
                                empty++;
                            }
                            j++;
                            break;
                        case "radio":
                        case "checkbox":
                            if (!elements[i].checked) {
                                empty++;
                            }
                            j++;
                            break;
                        case "select-one":
                        case "select-multi":
                            if (elements[i].selectedIndex == 0) {
                                empty++;
                            }
                            j++;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (empty == j) {
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    },
    getAttributes: function (elemento) {
        let dataAttributes = {};
        let attributesList = elemento.attributes;
        let atributos;
        for (let i = 0; i < attributesList.length; i++) {
            atributos = attributesList[i];
            if (atributos.nodeName.startsWith("data-")) {
                dataAttributes[atributos.nodeName.substring(5)] = atributos.nodeValue;
            }
        }
        return dataAttributes;
    }
}
