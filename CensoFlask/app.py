from flask import Flask, request, jsonify

def create_app():
    app = Flask(__name__, instance_relative_config=False)
    app.secret_key = 'hola'
    with app.app_context():
        from routes.route import router
        app.register_blueprint(router)
    return app