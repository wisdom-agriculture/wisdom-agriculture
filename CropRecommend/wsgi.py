import os
from django.core.wsgi import get_wsgi_application
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'server.settings')
application = get_wsgi_application()

# ML registry
import inspect
from apps.ml.registry import MLRegistry

try:
    registry = MLRegistry()

except Exception as e:
    print("Exception while loading the algorithms to the registry,", str(e))