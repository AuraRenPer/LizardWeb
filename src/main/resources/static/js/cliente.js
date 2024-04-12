
const API_URL = "http://localhost:8080";
console.log("cargando js correctamente");
function entrar() {
    console.log("entrando...");
    listarAdmisiones();
    listarOfertas();


    const formA = document.getElementById('frm');
    formA.addEventListener('submit', function (e) {
        e.preventDefault();
        enviarFrmAdd();
        });
}

async function listarAdmisiones() {
    const tabla = document.querySelector("#tbl-admi");
    await fetch(`${API_URL}/api/admisiones-cliente`)
            .then((response) => response.json())
            .then((admisiones) => {
                console.log(admisiones);
                const admisionesActivas = admisiones.filter((dato) => dato.activo);

                if (admisionesActivas.length > 0) {
                    admisionesActivas.forEach((dato) => {
                        let elemTr = document.createElement("tr");
                        let elemTd1 = document.createElement("td");
                        elemTd1.innerHTML = `${dato.nombreAdmision}`;
                        elemTr.appendChild((elemTd1));
                        tabla.appendChild(elemTr);
                    });
                }
            });
}


async function listarOfertas() {
    const tableBody = document.querySelector("#body");
    try {
        const response = await fetch(`${API_URL}/api/ofertaeducativa-cliente`);
        const datos = await response.json();
        console.log(datos); // Agrega esta línea para ver los datos en la consola

        // Limpiar el cuerpo de la tabla
        tableBody.innerHTML = "";
        // Iterar sobre los datos y agregar filas a la tabla
        datos.forEach((dato) => {
            console.log("Oferta educativa: ", dato.ofertaEducativa);
            console.log("Ocupaciones: ", dato.ocupaciones);
            const row = document.createElement("tr");
            // Agregar columnas a la fila
            const cell1 = document.createElement("td");
            cell1.textContent = dato.ofertaEducativa;
            row.appendChild(cell1);
            const cell2 = document.createElement("td");
            const ul = document.createElement("ul");
            if (dato.ocupaciones && dato.ocupaciones.length > 0) {
                console.log(dato.ocupaciones);
                dato.ocupaciones.forEach((ocu) => {
                    const li = document.createElement("li");
                    li.textContent = ocu.ocupacion;
                    console.log("Ocupaciones: ", ocu.ocupacion);
                    ul.appendChild(li);
                });
            }

            cell2.appendChild(ul);
            row.appendChild(cell2);
            // Agregar la fila al cuerpo de la tabla
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error(error);
    }
}

