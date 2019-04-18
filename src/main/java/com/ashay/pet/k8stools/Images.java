package com.ashay.pet.k8stools;

import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class Images {

    private final KubernetesClient kubernetesClient;

    @Autowired
    public Images(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @GetMapping
    public List<String> getAllImages() {
        return kubernetesClient.apps().deployments().inNamespace("default").list().getItems().stream()
                .map(deployment -> deployment.getSpec().getTemplate().getSpec().getContainers().get(0).getImage())
                .collect(Collectors.toList());
    }

}
