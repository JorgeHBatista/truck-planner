# REST API

## Ports

### List of ports
```
curl -X GET localhost:8080/v1/ports | json_pp
[
   {
      "id" : "5163ec2b-31db-4cd8-af3a-e88152945dc9",
      "latitude" : -5.84789683155603,
      "longitude" : 44.9092143130873,
      "name" : "gfMuQHriuB"
   },
   {
      "id" : "f629f538-9380-421e-af03-745273a9ead8",
      "latitude" : -51.340789231858,
      "longitude" : -109.746727517607,
      "name" : "nLfLCnNehV"
   },
   {
      "id" : "4613cf2c-1635-4f28-b789-941e7e990fed",
      "latitude" : -83.9941132239815,
      "longitude" : -90.7239056347347,
      "name" : "YxDbeMXpho"
   },
   {
      "id" : "8e76d345-04f0-46eb-aa53-f32cd34de714",
      "latitude" : 57.2823189298411,
      "longitude" : -166.805443602249,
      "name" : "rIjaKhQPUj"
   },
   {
      "id" : "9ad98c9a-1f7a-46e5-bfad-78a449d410f1",
      "latitude" : -28.0661234888792,
      "longitude" : 156.249304916094,
      "name" : "ndqXbEYJLb"
   },
   {
      "id" : "0fe5f6d2-310f-45e2-8166-ac2f07e8bce8",
      "latitude" : 27.4016288303582,
      "longitude" : -39.2124686458316,
      "name" : "xMTyrFBTvk"
   },
   {
      "id" : "b1d4b423-2e29-42ab-99ce-2475cea916c6",
      "latitude" : -26.8911716038455,
      "longitude" : 140.853965532001,
      "name" : "DRKfxmVvAH"
   }
]
```

### Get a port
Requesting information of the port with identifier `58922e63-5d6b-45ae-b966-033b8300373f`:
```
curl -X GET localhost:8080/v1/ports/4613cf2c-1635-4f28-b789-941e7e990fed | json_pp
{
   "id" : "4613cf2c-1635-4f28-b789-941e7e990fed",
   "latitude" : -83.9941132239815,
   "longitude" : -90.7239056347347,
   "name" : "YxDbeMXpho"
}

```

## Request estimation of turnaround time

In the following example, time `10:15:30` of `2022-12-03` (month `12` and day `3`) of port `58922e63-5d6b-45ae-b966-033b8300373f`:
```
curl -X GET localhost:8080/v1/ports/4613cf2c-1635-4f28-b789-941e7e990fed?arrivalTime=2022-12-03T10:15:30Z | json_pp
{
   "arrivalTime" : "2022-12-03T10:15:30Z",
   "id" : "4613cf2c-1635-4f28-b789-941e7e990fed",
   "latitude" : -83.9941132239815,
   "longitude" : -90.7239056347347,
   "name" : "YxDbeMXpho",
   "turnaroundTime" : 1684
}
```

## Bookings

### List of bookings
```
curl localhost:8080/v1/bookings/ | json_pp
[
   {
      "arrivalTime" : "2022-12-03T10:15:30Z",
      "driver" : "4613cf2c-1635-4f28-b789-941e7e990fed",
      "id" : "053af3e0-7469-4f28-bb18-5418058649da",
      "port" : "4613cf2c-1635-4f28-b789-941e7e990fed",
      "waitingArea" : "Mcwm"
   }
]
```
## Get a booking
```
curl localhost:8080/v1/bookings/c9093956-8548-40e0-9ccd-b20b3133e9f0 | json_pp
{
   "arrivalTime" : "2022-12-03T10:15:30Z",
   "driver" : "4613cf2c-1635-4f28-b789-941e7e990fed",
   "id" : "c9093956-8548-40e0-9ccd-b20b3133e9f0",
   "port" : "4613cf2c-1635-4f28-b789-941e7e990fed",
   "waitingArea" : "agxZ"
}
```

### Post a booking
Book a port at a particular time:
```
curl -X POST localhost:8080/v1/bookings -H 'Content-Type: application/json' -d '{"driver": "4613cf2c-1635-4f28-b789-941e7e990fed", "port": "4613cf2c-1635-4f28-b789-941e7e990fed", "arrivalTime": "2022-12-03T10:15:30Z" }' | json_pp
{
   "arrivalTime" : "2022-12-03T10:15:30Z",
   "driver" : "4613cf2c-1635-4f28-b789-941e7e990fed",
   "id" : "c9093956-8548-40e0-9ccd-b20b3133e9f0",
   "port" : "4613cf2c-1635-4f28-b789-941e7e990fed",
   "waitingArea" : "agxZ"
}
```