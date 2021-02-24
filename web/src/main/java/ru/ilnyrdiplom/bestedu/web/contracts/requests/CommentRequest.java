package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.requests.CommentRequestFacade;

@Getter
@Setter
public class CommentRequest implements CommentRequestFacade {
    private String text;
    private Integer parentId;
}
