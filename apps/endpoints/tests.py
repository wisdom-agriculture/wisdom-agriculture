from django.test import TestCase
from rest_framework.test import APIClient

class EndpointTests(TestCase):

    def test_predict_view(self):
        client = APIClient()
        input_data = {
            "N": 85,
            "P": 58,
            "K": 41,
            "temperature": 21.77046169,
            "humidity": 80.31964408,
            "ph": None,
            "rainfall": 226.6555374
        }
        classifier_url = "/api/v1/crop_recommend/predict"
        response = client.post(classifier_url, input_data, format='json')
        self.assertEqual('OK', response['status'])
        self.assertTrue('label' in response)
        self.assertEqual('rice', response['label'])
        self.assertTrue("request_id" in response.data)
        self.assertTrue("status" in response.data)