package com.usermanagement.model.processors;

import com.usermanagement.controller.TeamController;
import com.usermanagement.controller.UserController;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Gets triggered when we use e.g. the EntityModel.of() in our TeamController.get()
@Component
public class TeamProcessor implements RepresentationModelProcessor<EntityModel<Team>> {

    @Override
    public EntityModel<Team> process(EntityModel<Team> model) {

        // remove automatic generated links if necessary
         model.removeLinks();

        // add our custom links
        model.add(linkTo(methodOn(TeamController.class).get(model.getContent().getId(), null)).withSelfRel());
        model.add(linkTo(methodOn(TeamController.class).findAllUsersForTeam(model.getContent().getId(), null, null)).withRel("users"));

        return model;
    }

}
