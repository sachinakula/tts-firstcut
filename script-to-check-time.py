# Using python3
# Ensure that 'requests' library is installed in the environment, by using the command "pip3 install requests"

import requests
import time

payload = {'text': 'I will sing a song for sure tomorrow in a better way and peacefully and very slowly',
'voice': 'en-US-Chirp3-HD-Charon'}

files=[

]

headers = {}

url = "http://localhost:8080/v1/convert/wav/stream"

try:
    start_time = time.monotonic()
    response = requests.request("POST", url, headers=headers, data=payload, files=files, stream=True)
    response.raise_for_status()  # Raise an exception for bad status codes (4xx or 5xx)

    first_byte_received_time = None
    for chunk in response.iter_content(chunk_size=1):
        if first_byte_received_time is None:
            first_byte_received_time = time.monotonic()
            break  # Stop after receiving the first byte

    if first_byte_received_time:
        ttfb = first_byte_received_time - start_time
        print(f"Time to First Byte (TTFB): {ttfb:.4f} seconds")
    else:
        print("No content received or unable to determine first byte arrival.")

    totaltime = time.monotonic() - start_time

    print(f"Time to get full audio   : {totaltime:.4f} seconds")

except requests.exceptions.RequestException as e:
    print(f"Request failed: {e}")
