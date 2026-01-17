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