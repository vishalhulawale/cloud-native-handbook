$ kubectl apply -f namespace.yml 
namespace/admin-app created

$ kubectl get namespaces
NAME              STATUS   AGE
admin-app         Active   72s
default           Active   5h21m
kube-node-lease   Active   5h21m
kube-public       Active   5h21m
kube-system       Active   5h21m

Update C:\Windows\System32\drivers\etc\hosts
Add follwing entry
127.0.0.1       adminapp.localhost

$ kubectl apply -f admingui.deployment.yml
deployment.apps/admin-gui created

$ kubectl get deployments -n admin-app
NAME        READY   UP-TO-DATE   AVAILABLE   AGE
admin-gui   0/2     2            0           68s

$ kubectl get pods -n admin-app
NAME                         READY   STATUS    RESTARTS   AGE
admin-gui-5769b7cfb4-427g9   1/1     Running   0          4s
admin-gui-5769b7cfb4-dt74c   1/1     Running   0          4s

$ kubectl rollout restart deployment admin-gui -n admin-app

$ kubectl apply -f admingui.service.yml -n admin-app
service/admin-gui created

$ kubectl get services -n admin-app
NAME        TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)   AGE
admin-gui   ClusterIP   10.108.208.77   <none>        80/TCP    13m


$ kubectl apply -f userservice.deployment.yml
deployment.apps/userservice created


$ kubectl get deployments -n admin-app
NAME          READY   UP-TO-DATE   AVAILABLE   AGE
admin-gui     2/2     2            2           16m
userservice   2/2     2            2           58s

$ kubectl get pods -n admin-app
NAME                           READY   STATUS    RESTARTS   AGE
admin-gui-5769b7cfb4-427g9     1/1     Running   0          17m
admin-gui-5769b7cfb4-dt74c     1/1     Running   0          17m
userservice-5655cbb4bc-gvmb6   1/1     Running   0          85s
userservice-5655cbb4bc-pzj97   1/1     Running   0          85s

$ kubectl apply -f userservice.service.yml -n admin-app
service/userservice created

$ kubectl get services -n admin-app
NAME          TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
admin-gui     ClusterIP   10.108.208.77    <none>        80/TCP     21m
userservice   ClusterIP   10.108.204.244   <none>        8080/TCP   20s

$ kubectl apply -f ingress.yml -n admin-app
ingress.networking.k8s.io/admin-app-ingress created

$ kubectl get ingress -n admin-app
NAME                CLASS    HOSTS                ADDRESS   PORTS   AGE
admin-app-ingress   <none>   adminapp.localhost             80      18s

$ kubectl rollout restart deployment admin-gui -n admin-app
deployment.apps/admin-gui restarted

$ kubectl rollout restart deployment userservice -n admin-app
deployment.apps/userservice restarted

$ kubectl get deploy,sts,ds,svc,ingress,cm,secret,pvc,hpa -n admin-app
NAME                          READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/admin-gui     2/2     2            2           39m
deployment.apps/userservice   2/2     2            2           24m

NAME                  TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
service/admin-gui     ClusterIP   10.108.208.77    <none>        80/TCP     38m
service/userservice   ClusterIP   10.108.204.244   <none>        8080/TCP   16m

NAME                                          CLASS   HOSTS                ADDRESS     PORTS   AGE
ingress.networking.k8s.io/admin-app-ingress   nginx   adminapp.localhost   localhost   80      13m

NAME                         DATA   AGE
configmap/kube-root-ca.crt   1      55m

hulaw@vishal MINGW64 /c/Workspace/cloud-native-handbook/k8s/images/deployment (main)
$ kubectl apply -f envoy-configmap.yml
configmap/envoy-config created

hulaw@vishal MINGW64 /c/Workspace/cloud-native-handbook/k8s/images/deployment (main)
$ kubectl apply -f admingui.deployment.yml
deployment.apps/admin-gui configured

hulaw@vishal MINGW64 /c/Workspace/cloud-native-handbook/k8s/images/deployment (main)
$ kubectl apply -f admingui.service.yml
service/admin-gui configured

$ kubectl get pods -n admin-app
NAME                           READY   STATUS    RESTARTS   AGE
admin-gui-57c59757f9-8mnpq     2/2     Running   0          17m
admin-gui-57c59757f9-bd567     2/2     Running   0          17m
userservice-7cdd45997f-vjjcn   2/2     Running   0          12m
userservice-7cdd45997f-xnfp8   2/2     Running   0          12m

hulaw@vishal MINGW64 /c/Workspace/cloud-native-handbook (main)
$ kubectl describe pod admin-gui-57c59757f9-8mnpq -n admin-app
Name:             admin-gui-57c59757f9-8mnpq
Namespace:        admin-app
Priority:         0
Service Account:  default

$ kubectl logs admin-gui-57c59757f9-8mnpq -c envoy -n admin-app
[2026-01-17 15:43:25.987][1][info][main] [source/server/server.cc:404] initializing epoch 0 (base id=0, hot restart version=11.104)
[2026-01-17 15:43:25.988][1][info][main] [source/server/server.cc:406] statically linked extensions:
[2026-01-17 15:43:25.988][1][info][main] [source/server/server.cc:408]   envoy.formatter: envoy.formatter.metadata, envoy.formatter.req_without_query
[2026-01-17 15:43:25.988][1][info][main] [source/server/server.cc:408]   envoy.http.header_validators: envoy.http.header_validators.envoy_default


Envoy to istio

$ kubectl label namespace admin-app istio-injection=enabled --overwrite
namespace/admin-app labeled

$ kubectl apply -f k8s/images/deployment/userservice.deployment.yml
$ kubectl apply -f k8s/images/deployment/admingui.deployment.yml
$ kubectl apply -f k8s/images/deployment/userservice.service.yml
$ kubectl apply -f k8s/images/deployment/admingui.service.yml
deployment.apps/userservice configured
deployment.apps/admin-gui configured
service/userservice configured
service/admin-gui configured

Download and Install Istio
https://github.com/istio/istio/releases/tag/1.27.0


istioctl install --set profile=demo -y

kubectl apply -f k8s/images/deployment/ingress.yml
gateway.networking.istio.io/admin-app-gateway created
virtualservice.networking.istio.io/admin-gui-vs created

hulaw@vishal MINGW64 /c/Workspace/cloud-native-handbook (main)
$ kubectl delete -f k8s/images/deployment/envoy-configmap.yml --ignore-not-found
kubectl delete -f k8s/images/deployment/envoy-configmap-userservice.yml --ignore-not-found
configmap "envoy-config" deleted
configmap "envoy-config-userservice" deleted

hulaw@vishal MINGW64 /c/Workspace/cloud-native-handbook (main)
$ kubectl delete namespace ingress-nginx 2>&1
namespace "ingress-nginx" deleted