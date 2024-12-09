from flask import Blueprint, abort, jsonify, request, render_template, redirect,url_for, flash
import requests
router = Blueprint('router', __name__)




@router.route('/')
def index():
    return render_template('home.html')

@router.route('/censoList')
def home():
    r = requests.get('http://localhost:8080/myapp/censo/all')
    data =  r.json()
    return render_template('censoAll.html', lista = data["data"])

@router.route('/saveCenso', methods=['GET', 'POST'])
def save_censo():
    if request.method == 'POST':
    
        censo_data = {
            "id": request.form['id'], 
            "provincia": request.form['provincia'],
            "totalFamilias": request.form['totalFamilias'],
            "familiasConGenerador": request.form['familiasConGenerador'],
        }
    
        response = requests.post('http://localhost:8080/myapp/censo/save', json=censo_data)
        if response.status_code == 200:
            return redirect(url_for('router.home'))  
        else:
            return render_template('saveCenso.html', error=response.json()["data"])
    
    return render_template('saveCenso.html')

@router.route('/getCenso/<int:id>', methods=['GET'])
def get_censo(id):
    
    response = requests.get(f'http://localhost:8080/myapp/censo/get/{id}')
    
    if response.status_code == 200:
        censo_data = response.json()["data"]
        return render_template('censoDetail.html', censo=censo_data)
    else:
        return render_template('censoDetail.html', error=response.json().get("data", "Error al recuperar el censo"))
    

@router.route('/updateCenso/<int:censo_id>', methods=['GET', 'POST'])
def update_censo(censo_id):
    if request.method == 'POST':
    
        censo_data = {
            "id": censo_id,
            "provincia": request.form['provincia'],
            "totalFamilias": request.form['totalFamilias'],
            "familiasConGenerador": request.form['familiasConGenerador'],
        }

    
        response = requests.post('http://localhost:8080/myapp/censo/update', json=censo_data)

        if response.status_code == 200:
            flash("Censo actualizado correctamente", "success")
            return redirect(url_for('router.home'))  
        else:
            flash(response.json().get("data", "Error al actualizar el censo"), "danger")

    
    response = requests.get(f'http://localhost:8080/myapp/censo/get/{censo_id}')
    if response.status_code == 200:
        censo = response.json().get("data", {})
    else:
        flash("No se pudo encontrar el censo", "danger")
        return redirect(url_for('router.home'))

    return render_template('updateCenso.html', censo=censo)


@router.route('/deleteCenso/<int:id>', methods=['POST'])
def delete_censo(id):
    
    response = requests.post(f'http://localhost:8080/myapp/censo/delete/{id}')
    
    if response.status_code == 200:
        flash('Censo eliminado correctamente', 'success')
        return redirect(url_for('router.home'))  
    else:
        flash(response.json().get("info", "Error al eliminar el censo"), 'danger')
        return redirect(url_for('router.home'))  


@router.route('/orderCenso/<criterio>/<tipo>/<metodo>')
def ordenar_censos(criterio, tipo, metodo):
    url = "http://localhost:8080/myapp/censo/"
    

    if metodo == 'merge':
        endpoint = "mergeOrder"
    elif metodo == 'quick':
        endpoint = "quickOrder"
    elif metodo == 'shell':
        endpoint = "shellOrder"
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": "Método no válido"}), 400


    final_url = f"{url}{endpoint}/{criterio}/{tipo}"

    try:
        r = requests.get(final_url)
        data = r.json()
    except requests.exceptions.RequestException as e:
        return jsonify({"msg": "ERROR", "data": [], "error": str(e)}), 500


    if r.status_code == 200 and data["msg"] == "OK":
        return jsonify({"msg": "OK", "lista": data.get("data", [])})
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": data.get("data", "No se pudo procesar la solicitud.")}), r.status_code

@router.route('/searchCenso/<criterio>/<metodo>/<value>')
def buscar_censos(metodo, criterio, value):  
    url = "http://localhost:8080/myapp/censo/"
    

    if metodo == 'linear':
        endpoint = "linearSearch"
    elif metodo == 'binary':
        endpoint = "binarySearch"
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": "Método no válido"}), 400

    
    final_url = f"{url}{endpoint}/{criterio}/{value}"

    
    try:
        r = requests.get(final_url)
        data = r.json()
    except requests.exceptions.RequestException as e:
        return jsonify({"msg": "ERROR", "data": [], "error": str(e)}), 500


    if r.status_code == 200 and data["msg"] == "OK":
        return jsonify({"msg": "OK", "lista": data.get("data", [])})
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": data.get("data", "No se pudo procesar la solicitud.")}), r.status_code







@router.route('/generadorList')
def generador_list():
    r = requests.get('http://localhost:8080/myapp/generador/all')
    data =  r.json()
    return render_template('generadorAll.html', lista = data["data"])


@router.route('/saveGenerador', methods=['GET', 'POST'])
def save_generador():
    if request.method == 'POST':
        
        generador_data = {
            "id": request.form['id'], 
            "marca": request.form['marca'],
            "potenciaGenerada": request.form['potenciaGenerada'],
            "costo": request.form['costo'],
            "consumoPorHora": request.form['consumoPorHora'],
        }
    
        response = requests.post('http://localhost:8080/myapp/generador/save', json=generador_data)
        if response.status_code == 200:
            return redirect(url_for('router.generador_list'))  
        else:
            return render_template('saveGenerador.html', error=response.json()["data"])
    
    return render_template('saveGenerador.html')


@router.route('/getGenerador/<int:id>', methods=['GET'])
def get_generador(id):
    response = requests.get(f'http://localhost:8080/myapp/generador/get/{id}')
    if response.status_code == 200:
        generador_data = response.json()["data"]
        return render_template('generadorDetail.html', generador=generador_data)
    else:
        return render_template('generadorDetail.html', error=response.json()["data"])


@router.route('/updateGenerador/<int:id>', methods=['GET', 'POST'])
def update_generador(id):
    if request.method == 'POST':
    
        generador_data = {
            "id": id,  
            "marca": request.form['marca'],
            "potenciaGenerada": request.form['potenciaGenerada'],
            "costo": request.form['costo'],
            "consumoPorHora": request.form['consumoPorHora']
        }
        
        
        response = requests.post('http://localhost:8080/myapp/generador/update', json=generador_data)
        if response.status_code == 200:
            return redirect(url_for('router.generador_list'))
        else:
            return render_template('updateGenerador.html', error=response.json()["data"], generador=generador_data)
    

    response = requests.get(f'http://localhost:8080/myapp/generador/get/{id}')
    if response.status_code == 200:
        generador = response.json()["data"]
        return render_template('updateGenerador.html', generador=generador)
    else:
        return render_template('updateGenerador.html', error=response.json()["data"])
    
@router.route('/deleteGenerador/<int:id>', methods=['POST'])
def delete_generador(id):
    response = requests.delete(f'http://localhost:8080/myapp/generador/delete/{id}')
    
    if response.status_code == 200:
        flash('Generador eliminado correctamente', 'success')
        return redirect(url_for('router.generador_list'))  
    else:
        flash(response.json().get("info", "Error al eliminar el generador"), 'danger')
        return redirect(url_for('router.generador_list'))  


@router.route('/orderGenerador/<criterio>/<tipo>/<metodo>')
def ordenar_generadores(criterio, tipo, metodo):
    url = "http://localhost:8080/myapp/generador/"
    

    if metodo == 'merge':
        endpoint = "mergeOrder"
    elif metodo == 'quick':
        endpoint = "quickOrder"
    elif metodo == 'shell':
        endpoint = "shellOrder"
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": "Método no válido"}), 400


    final_url = f"{url}{endpoint}/{criterio}/{tipo}"

    try:
        r = requests.get(final_url)
        data = r.json()
    except requests.exceptions.RequestException as e:
        return jsonify({"msg": "ERROR", "data": [], "error": str(e)}), 500


    if r.status_code == 200 and data["msg"] == "OK":
        return jsonify({"msg": "OK", "lista": data.get("data", [])})
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": data.get("data", "No se pudo procesar la solicitud.")}), r.status_code
    

@router.route('/searchGenerador/<criterio>/<metodo>/<value>')
def buscar_generadores(metodo, criterio, value):  
    url = "http://localhost:8080/myapp/generador/"
    
    
    if metodo == 'linear':
        endpoint = "linearSearch"
    elif metodo == 'binary':
        endpoint = "binarySearch"
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": "Método no válido"}), 400


    final_url = f"{url}{endpoint}/{criterio}/{value}"

    try:
        r = requests.get(final_url)
        data = r.json()
    except requests.exceptions.RequestException as e:
        return jsonify({"msg": "ERROR", "data": [], "error": str(e)}), 500

    
    if r.status_code == 200 and data["msg"] == "OK":
        return jsonify({"msg": "OK", "lista": data.get("data", [])})
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": data.get("data", "No se pudo procesar la solicitud.")}), r.status_code
    









@router.route('/familiaList')
def familia_list():
    r = requests.get('http://localhost:8080/myapp/familia/all')
    data = r.json()
    return render_template('familiaAll.html', lista=data["data"])

@router.route('/saveFamilia', methods=['GET', 'POST'])
def save_familia():
    if request.method == 'POST':
        
        familia_data = {
            "tieneGenerador": request.form['tieneGenerador'] == 'True',
            "cantidadPersonas": request.form['cantidadPersonas'],
            "razonUso": request.form['razonUso']
        }
        
        response = requests.post('http://localhost:8080/myapp/familia/save', json=familia_data)
        if response.status_code == 200:
            return redirect(url_for('router.familia_list'))  
        else:
            return render_template('saveFamilia.html', error=response.json()["data"])
    
    return render_template('saveFamilia.html')


@router.route('/getFamilia/<int:id>', methods=['GET'])
def get_familia(id):
    response = requests.get(f'http://localhost:8080/myapp/familia/get/{id}')
    if response.status_code == 200:
        familia_data = response.json()["data"]
        return render_template('familiaDetail.html', familia=familia_data)
    else:
        return render_template('familiaDetail.html', error=response.json()["data"])


@router.route('/updateFamilia/<int:id>', methods=['GET', 'POST'])
def update_familia(id):
    if request.method == 'POST':
        
        familia_data = {
            "id": id, 
            "tieneGenerador": request.form['tieneGenerador'] == 'True',  
            "cantidadPersonas": request.form['cantidadPersonas'],
            "razonUso": request.form['razonUso']
        }
        
    
        response = requests.post('http://localhost:8080/myapp/familia/update', json=familia_data)
        if response.status_code == 200:
            return redirect(url_for('router.familia_list')) 
        else:
            return render_template('updateFamilia.html', error=response.json()["data"], familia=familia_data)
    
    
    response = requests.get(f'http://localhost:8080/myapp/familia/get/{id}')
    if response.status_code == 200:
        familia = response.json()["data"]
        return render_template('updateFamilia.html', familia=familia)
    else:
        return render_template('updateFamilia.html', error=response.json()["data"])


@router.route('/deleteFamilia/<int:id>', methods=['POST'])
def delete_familia(id):
    
    response = requests.delete(f'http://localhost:8080/myapp/familia/delete/{id}')
    
    if response.status_code == 200:
        flash('Familia eliminada correctamente', 'success')
        return redirect(url_for('router.familia_list'))  
    else:
        flash(response.json().get("info", "Error al eliminar la familia"), 'danger')
        return redirect(url_for('router.familia_list'))  
    

@router.route('/orderFamilia/<criterio>/<tipo>/<metodo>')
def ordenar_familias(criterio, tipo, metodo):  
    url = "http://localhost:8080/myapp/familia/"
    
    
    if metodo == 'merge':
        endpoint = "mergeOrder"
    elif metodo == 'quick':
        endpoint = "quickOrder"
    elif metodo == 'shell':
        endpoint = "shellOrder"
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": "Método no válido"}), 400

    
    final_url = f"{url}{endpoint}/{criterio}/{tipo}"

    
    try:
        r = requests.get(final_url)
        data = r.json()
    except requests.exceptions.RequestException as e:
        return jsonify({"msg": "ERROR", "data": [], "error": str(e)}), 500

    
    if r.status_code == 200 and data["msg"] == "OK":
        return jsonify({"msg": "OK", "lista": data.get("data", [])})
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": data.get("data", "No se pudo procesar la solicitud.")}), r.status_code


@router.route('/searchFamilia/<criterio>/<metodo>/<value>')
def buscar_familias(metodo, criterio, value):  
    url = "http://localhost:8080/myapp/familia/"
    
    
    if metodo == 'linear':
        endpoint = "linearSearch"
    elif metodo == 'binary':
        endpoint = "binarySearch"
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": "Método no válido"}), 400


    final_url = f"{url}{endpoint}/{criterio}/{value}"

    try:
        r = requests.get(final_url)
        data = r.json()
    except requests.exceptions.RequestException as e:
        return jsonify({"msg": "ERROR", "data": [], "error": str(e)}), 500

    
    if r.status_code == 200 and data["msg"] == "OK":
        return jsonify({"msg": "OK", "lista": data.get("data", [])})
    else:
        return jsonify({"msg": "ERROR", "data": [], "error": data.get("data", "No se pudo procesar la solicitud.")}), r.status_code






@router.route('/historial')
def historial_list():
    r = requests.get('http://localhost:8080/myapp/historial')  
    data = r.json()
    return render_template('historialAll.html', lista=data["data"])
